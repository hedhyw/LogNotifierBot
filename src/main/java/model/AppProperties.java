package model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AppProperties {

  private static final String NODE_BOT = "bot";
  private static final String NODE_BOT_NAME = "name";
  private static final String NODE_BOT_TOKEN = "token";
  private static final String NODE_BOT_MESSAGES = "messages";
  private static final String NODE_BOT_STARTUP_MESSAGE = "hello";
  private static final String NODE_BOT_ACCESS_DENIED_MESSAGE = "accessdenied";
  private static final String NODE_BOT_ACCEPT_REQUEST_MESSAGE = "accept";
  private static final String NODE_BOT_CANCEL_REQUEST_MESSAGE = "cancel";

  private static final String NODE_LOGS = "logs";
  private static final String NODE_LOG = "logfile";
  private static final String NODE_LOG_PARSER = "parser";
  private static final String NODE_LOG_FILENAME = "filename";
  private static final String NODE_LOG_PROCESS_FILTERS = "processfilters";
  private static final String NODE_LOG_PROCESS_FILTER_NAME = "name";

  private static final String NODE_SUPERUSERS = "superusers";
  private static final String NODE_SUPERUSERNAME = "username";

  private BotInfo botInfo;
  private List<String> superUsers;
  private List<LogInfo> logs;

  public AppProperties()
    throws IOException, ParserConfigurationException,
           SAXException {
      File file = new File(Constants.PROPERTIES_XML_FILE);
      if (!file.canRead()) throw new IOException();

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(file);
      doc.getDocumentElement().normalize();
      NodeList childNodes = doc.getDocumentElement().getChildNodes();
      for (int i = 0; i < childNodes.getLength(); ++i) {
        Node node = childNodes.item(i);
        if (node.getNodeType() != Node.ELEMENT_NODE) continue;
        Element element = (Element) node;
        switch (element.getTagName().toLowerCase()) {
          case NODE_BOT:
            botInfo = getBotInfo(element);
            break;
          case NODE_SUPERUSERS:
            superUsers = getSuperUsers(element);
            break;
          case NODE_LOGS:
            logs = getLogs(element);
            break;
        }
      };
  }

  protected BotInfo getBotInfo(Element botElement) {
    String botName = Constants.NOT_DEFINED;
    String botToken = Constants.NOT_DEFINED;
    BotMessages botMessages = null;
    NodeList childNodes = botElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      switch(element.getTagName().toLowerCase()) {
        case NODE_BOT_NAME:
          botName = element.getTextContent();
          break;
        case NODE_BOT_TOKEN:
          botToken = element.getTextContent();
          break;
        case NODE_BOT_MESSAGES:
          botMessages = getBotMessages(element);
          break;
      }
    }
    return new BotInfo(botName, botToken, botMessages);
  }

  protected BotMessages getBotMessages(Element messagesElement) {
    String startup = Constants.NOT_DEFINED;
    String accessDenied = Constants.NOT_DEFINED;
    String acceptRequest = Constants.NOT_DEFINED;
    String cancelRequest = Constants.NOT_DEFINED;
    NodeList childNodes = messagesElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      switch(element.getTagName().toLowerCase()) {
        case NODE_BOT_STARTUP_MESSAGE:
          startup = element.getTextContent();
          break;
        case NODE_BOT_ACCESS_DENIED_MESSAGE:
          accessDenied = element.getTextContent();
          break;
        case NODE_BOT_ACCEPT_REQUEST_MESSAGE:
          acceptRequest = element.getTextContent();
          break;
        case NODE_BOT_CANCEL_REQUEST_MESSAGE:
          cancelRequest = element.getTextContent();
          break;
      }
    }
    return new BotMessages(startup, accessDenied, acceptRequest, cancelRequest);
  }

  protected List<String> getSuperUsers(Element superusersElement) {
    List<String> users = new ArrayList<String>();
    NodeList childNodes = superusersElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      if (element.getTagName().toLowerCase() == NODE_SUPERUSERNAME)
        users.add(element.getTextContent());
    }
    return Collections.unmodifiableList(users);
  }

  protected List<LogInfo> getLogs(Element logsElement) {
    List<LogInfo> logs = new ArrayList<LogInfo>();
    NodeList childNodes = logsElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      if (element.getTagName().toLowerCase() == NODE_LOG)
        logs.add(getLog(element));
    }
    return Collections.unmodifiableList(logs);
  }

  protected LogInfo getLog(Element logInfoElement) {
    String parserName = Constants.NOT_DEFINED;
    String fileName = Constants.NOT_DEFINED;
    List<String> processNames = new ArrayList<String>();;
    NodeList childNodes = logInfoElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      switch(element.getTagName().toLowerCase()) {
        case NODE_LOG_PARSER:
          parserName = element.getTextContent();
          break;
        case NODE_LOG_FILENAME:
          fileName = element.getTextContent();
          break;
        case NODE_LOG_PROCESS_FILTERS:
          processNames = getProcessNames(element);
          break;
      }
    }
    return new LogInfo(parserName, fileName, processNames);
  }

  protected List<String> getProcessNames(Element processNameElement) {
    List<String> processNames = new ArrayList<String>();;
    NodeList childNodes = processNameElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); ++i) {
      Node node = childNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;
      Element element = (Element) node;
      if (element.getTagName().toLowerCase() == NODE_LOG_PROCESS_FILTER_NAME)
        processNames.add(element.getTextContent().toLowerCase());
    }
    return processNames;
  }

  public BotInfo getBotInfo() {
    return botInfo;
  }

  public List<LogInfo> getLogs() {
    return logs;
  }

  public List<String> getSuperUsers() {
    return superUsers;
  }

}