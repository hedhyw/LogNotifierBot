package log.model;

import java.util.regex.Pattern;

public class AuthLogEntry extends LogEntry {

  private static final Pattern PROCESS_NAME_PATTERN =
    Pattern.compile("\\s(\\w+)\\[");;
  private static final Pattern DATE_PATTERN =
    Pattern.compile("^(.{15})");
  private static final Pattern MESSAGE_PATTERN =
    Pattern.compile("\\]:\\s(.*)$");

  public AuthLogEntry(String line) throws LogParseException {
    super(line);
  }

  @Override
  protected Pattern getDatePattern() {
    return DATE_PATTERN;
  }

  @Override
  protected Pattern getProcessNamePattern() {
    return PROCESS_NAME_PATTERN;
  }

  @Override
  protected Pattern getMessagePattern() {
    return MESSAGE_PATTERN;
  }

}