package ru.hedhyw.lognotifierbot;

import java.util.ArrayList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import ru.hedhyw.lognotifierbot.log.LogParser;
import ru.hedhyw.lognotifierbot.log.LogThread;
import ru.hedhyw.lognotifierbot.log.model.LogEntry;
import ru.hedhyw.lognotifierbot.log.model.OnNewLogsReceivedListener;
import ru.hedhyw.lognotifierbot.model.BotInfo;
import ru.hedhyw.lognotifierbot.model.BotMessages;
import ru.hedhyw.lognotifierbot.model.ChatIds;

public class LogNotifierBot extends TelegramBot
  implements UpdatesListener, OnNewLogsReceivedListener {

  private ChatIds chatIds;
  private BotMessages botMessages;
  private List<String> superUsers;
  List<LogParser> parsers;

  public LogNotifierBot(
    BotInfo botInfo,
    List<LogParser> parsers,
    List<String> superUsers) {
      super(botInfo.getToken());
      this.botMessages = botInfo.getMessages();
      this.superUsers = superUsers;
      this.parsers = parsers;
  }

  public void start() {
    setUpdatesListener(this);
    chatIds = ChatIds.getInstance();
    LogThread authLogThread = new LogThread(parsers);
    authLogThread.setOnNewLogsReceivedListener(this);
    authLogThread.start();
    notifyAll(botMessages.getStartup());
  }

  public void logsReceived(ArrayList<LogEntry> logs) {
    StringBuilder strBuilder = new StringBuilder();
    int number = 1;
    boolean isMultiElementArray = logs.size() > 1;
    for (LogEntry logEntry : logs) {
      if (isMultiElementArray) {
        strBuilder.append(number++);
        strBuilder.append(". ");
      }
      strBuilder.append(logEntry.getProcessName());
      strBuilder.append(": ");
      strBuilder.append(logEntry.getMessage());
      strBuilder.append('\n');
    }
    notifyAll(strBuilder.toString());
  }

  @Override
  public int process(List<Update> updates) {
    for (Update update : updates) {
      User user = update.message().from();
      if (!superUsers.contains(user.username())) {
        sendMessage(botMessages.getAccessDenied(), update.message().chat().id());
        continue;
      }
      Long chatId = update.message().chat().id();
      if (chatIds.contains(chatId)) {
        sendMessage(botMessages.getCancelRequest(), chatId);
      } else {
        chatIds.add(chatId);
        sendMessage(botMessages.getAcceptRequest(), chatId);
      }
    }
    return UpdatesListener.CONFIRMED_UPDATES_ALL;
  }

  private void notifyAll(String text) {
    for (Long chatId : chatIds) {
      sendMessage(text, chatId);
    }
  }

  private void sendMessage(String text, Long chatId) {
    SendMessage request = new SendMessage(chatId, text)
      .parseMode(ParseMode.Markdown)
      .disableWebPagePreview(true)
      .disableNotification(true);
    execute(request);
  }
}