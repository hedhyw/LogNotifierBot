package ru.hedhyw.lognotifierbot.log;

import java.util.ArrayList;
import java.util.List;

import ru.hedhyw.lognotifierbot.log.model.LogEntry;
import ru.hedhyw.lognotifierbot.log.model.OnNewLogsReceivedListener;
import ru.hedhyw.lognotifierbot.model.Constants;;

public class LogThread extends Thread {

  private List<LogParser> parsers;
  private OnNewLogsReceivedListener onNewLogsReceivedListener;

  public LogThread(List<LogParser> parsers) {
    this.parsers = parsers;
  }

  public void setOnNewLogsReceivedListener(
    OnNewLogsReceivedListener onNewLogsReceivedListener) {
      this.onNewLogsReceivedListener = onNewLogsReceivedListener;
  }

  @Override
  public void run() {
    while(true) {
      try {
        Thread.sleep(Constants.LOG_THREAD_SLEEP_TIME);
      } catch (InterruptedException ex) {
      }
      if (onNewLogsReceivedListener == null) continue;
      for (LogParser parser : parsers) {
        ArrayList<LogEntry> logs = parser.getNewLogs();
        if (logs.isEmpty()) continue;
        onNewLogsReceivedListener.logsReceived(logs);
      }
    }
  }

}