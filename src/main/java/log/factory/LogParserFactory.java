package log.factory;

import log.LogParser;

public interface LogParserFactory {

  public LogParser createLogParser();

}