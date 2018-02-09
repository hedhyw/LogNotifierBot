package ru.hedhyw.lognotifierbot.model;

public class BotMessages {

  private final String startup;
  private final String accessDenied;
  private final String acceptRequest;
  private final String cancelRequest;

  public BotMessages(
    String startup,
    String accessDenied,
    String acceptRequest,
    String cancelRequest) {
      this.startup = startup;
      this.accessDenied = accessDenied;
      this.acceptRequest = acceptRequest;
      this.cancelRequest = cancelRequest;
  }

  public String getStartup() {
    return startup;
  }

  public String getAccessDenied() {
    return accessDenied;
  }

  public String getAcceptRequest() {
    return acceptRequest;
  }

  public String getCancelRequest() {
    return cancelRequest;
  }

}