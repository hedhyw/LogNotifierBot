package log;

import java.util.ArrayList;
import java.util.List;

import log.model.LogEntry;
import log.model.OnNewLogsReceivedListener;
import model.Constants;;

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