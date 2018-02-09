package model;

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ChatIds extends AbstractList<Long> {

  private static final String NODE_ROOT = "chats";
  private static final String NODE_CHAT = "chat";
  private static final String ATTR_CHAT_ID = "id";
  private List<Long> chatIds;

  private ChatIds(List<Long> chatIds) {
    this.chatIds = chatIds;
  }

  public static ChatIds getInstance() {
    List<Long> chatIds = new ArrayList<Long>();
    try {
      File file = new File(Constants.CHATS_XML_FILE);
      SAXParserFactory saxFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxFactory.newSAXParser();
      SAXChatIdsHandler saxHandler =
        new SAXChatIdsHandler(NODE_CHAT, ATTR_CHAT_ID);
      saxParser.parse(file, saxHandler);
      chatIds = saxHandler.getChatIds();
    } catch(
      ParserConfigurationException |
      SAXException |
      IOException ex) {
    }
    return new ChatIds(chatIds);
  }

  public Long get(int index) {
    return chatIds.get(index);
  }

  public int size() {
    return chatIds.size();
  }

  @Override
  public boolean add(Long id) {
    if (chatIds.add(id)) {
      return save();
    } else {
      return false;
    }
  }

  @Override
  public boolean contains(Object id) {
    return chatIds.contains(id);
  }

  private boolean save() {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder;
    try {
      docBuilder = dbFactory.newDocumentBuilder();
    } catch(ParserConfigurationException ex) {
      return false;
    }
    Document doc = docBuilder.newDocument();

    Element root = doc.createElement(NODE_ROOT);
    doc.appendChild(root);
    for (Long chatId : chatIds) {
      Element elem = doc.createElement(NODE_CHAT);
      Attr attr = doc.createAttribute(ATTR_CHAT_ID);
      attr.setValue(chatId.toString());
      elem.setAttributeNode(attr);
      root.appendChild(elem);
    }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    try {
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(
        new File(Constants.CHATS_XML_FILE));
      transformer.transform(source, result);
    } catch (TransformerException ex) {
      return false;
    }
    return true;
  }

}