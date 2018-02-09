package ru.hedhyw.lognotifierbot.model;

public class BotInfo {

  private final String name;
  private final String token;
  private final BotMessages messages;

  BotInfo(String name, String token, BotMessages messages) {
    this.name = name;
    this.token = token;
    this.messages = messages;
  }

  public String getName() {
    return name;
  }

  public String getToken() {
    return token;
  }

  public BotMessages getMessages() {
    return messages;
  }

}