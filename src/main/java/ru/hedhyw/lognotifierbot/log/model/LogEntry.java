package ru.hedhyw.lognotifierbot.log.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LogEntry {

  protected abstract Pattern getDatePattern();
  protected abstract Pattern getProcessNamePattern();
  protected abstract Pattern getMessagePattern();

  private String message;
  private String date;
  private String processName;

  LogEntry(String line) throws LogParseException {
    Matcher matcher = getProcessNamePattern().matcher(line);
    if (!matcher.find()) throw new LogParseException();
    processName = matcher.group(1);

    matcher = getDatePattern().matcher(line);
    if (!matcher.find()) throw new LogParseException();
    date = matcher.group(1);

    matcher = getMessagePattern().matcher(line);
    if (!matcher.find()) throw new LogParseException();
    message = matcher.group(1);
  }

  public String getMessage() {
    return message;
  }

  public String getDate() {
    return date;
  }

  public String getProcessName() {
    return processName;
  }
}