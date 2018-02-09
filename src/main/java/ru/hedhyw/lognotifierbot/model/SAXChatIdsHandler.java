package ru.hedhyw.lognotifierbot.model;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class SAXChatIdsHandler extends DefaultHandler {

  private List<Long> chatIds;
  private String nodeChatTagName;
  private String attrChatIdName;

  public SAXChatIdsHandler(String nodeChatTagName, String attrChatIdName) {
    chatIds = new ArrayList<Long>();
    this.nodeChatTagName = nodeChatTagName;
    this.attrChatIdName = attrChatIdName;
  }

  public void startElement(String uri, String localName,String tagName,
    Attributes attributes) throws SAXException {
      if (tagName.equalsIgnoreCase(nodeChatTagName)) {
        String val = attributes.getValue(attrChatIdName);
        chatIds.add(Long.parseLong(val));
      }
  }

  public List<Long> getChatIds() {
    return chatIds;
  }

}