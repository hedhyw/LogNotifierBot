# LogNotifierBot
Bot for the telegram that sends new messages from sshd/auth.log. 

1. Build `gradle build`.
2. There are two archives in the build/distributions/ folder, unarchive one of them.
3. Start bot `./bin/LogNotifierBot`. A file **~/.LogNotifyerBot/properties.xml** will be created, edit it.
*In order to obtain token, write to the Bot Father, https://telegram.me/botfather.*

2. Run bot again and write smth to him in the Telegram.
If he answers **"access denied"**, you are not in the super users list (see properties.xml), else all OK.

# ~/.LogNotifyerBot/properties.xml
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<properties>
  <bot>
    <name>Auth Log Notifyer</name>
    <token>YOUR_AUTH_TOKEN</token>
    <messages>
      <hello>The bot is started.</hello>
      <accessdenied>Access denied.</accessdenied>
      <accept>You're added to list.</accept>
      <cancel>You're already in list.</cancel>
    </messages>
  </bot>
  <superusers>
    <username>YOUR_USERNAME</username>
  </superusers>
  <logs>
    <logfile>
      <parser>AuthLog</parser>
      <filename>/var/log/auth.log</filename>
      <processfilters>
        <name>sshd</name>
      </processfilters>
    </logfile>
  </logs>
</properties>
```
