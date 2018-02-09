package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Nullable;

import log.factory.LogEntryFactory;
import log.model.LogEntry;
import log.model.LogParseException;
import model.LogInfo;

public class LogParser {

  private String lastLogDate;
  private String logFileName;
  private List<String> processNamesFilter;
  private LogEntryFactory logEntryCreator;

  private LogParser(
    String logFileName,
    List<String> processNamesFilter,
    LogEntryFactory logEntryCreator) {
      this.logFileName = logFileName;
      this.processNamesFilter = processNamesFilter;
      this.logEntryCreator = logEntryCreator;
  }

  @Nullable
  public static LogParser of(LogInfo info) {
    LogEntryFactory logEntryCreator =
      LogEntryFactory.getCreatorByParserName(info.getParserNames());
    if (logEntryCreator == null) return null;
    return new LogParser(
      info.getFileName(),
      info.getprocessNames(),
      logEntryCreator);
  }

  public ArrayList<LogEntry> getNewLogs() {
    ArrayList<LogEntry> newLogs = new ArrayList<LogEntry>();
    ArrayList<LogEntry> logs = readLog();

    if (lastLogDate == null) {
      if (!logs.isEmpty()) {
        lastLogDate = logs.get(logs.size() - 1).getDate();
      }
      return newLogs;
    }

    boolean addNew = false;
    for (LogEntry logEntry : logs) {
      if (logEntry.getDate().equals(lastLogDate)) {
        addNew = true;
        continue;
      }
      if (!addNew) continue;
      newLogs.add(logEntry);
      lastLogDate = logEntry.getDate();
    }
    if (addNew == false) return logs;
    return newLogs;
  }

  private ArrayList<LogEntry> readLog() {
    ArrayList<LogEntry> logs = new ArrayList<LogEntry>();
    File file = new File(logFileName);
    if (!file.canRead()) return logs;
    try {
      Scanner s = new Scanner(file);
      while(s.hasNextLine()) {
        String line = s.nextLine();
        try {
          LogEntry entry = logEntryCreator.create(line);
          if (processNamesFilter.contains(entry.getProcessName().toLowerCase())) {
            logs.add(entry);
          }
        } catch(LogParseException ex) {
        }
      }
      s.close();
    } catch (FileNotFoundException ex) {
    }
    return logs;
  }

}