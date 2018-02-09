package ru.hedhyw.lognotifierbot.log;

import org.junit.Test;
import static org.junit.Assert.*;

import ru.hedhyw.lognotifierbot.log.model.AuthLogEntry;
import ru.hedhyw.lognotifierbot.log.model.LogParseException;

public class AuthLogEntryTest {

    private String date = "Mon  1 00:00:00";
    private String application = "APP";
    private String message = "msg";
    private final String validLine =
        String.format("%s HOST %s[0000]: %s",
            date, application, message);
    private final String invalidLine = "";

    @Test public void testEntryWithValidData() {
        try {
            AuthLogEntry entry = new AuthLogEntry(validLine);
            assertNotNull(entry);
            assertEquals(message, entry.getMessage());
            assertEquals(application, entry.getProcessName());
            assertEquals(date, entry.getDate());
        } catch(LogParseException ex) {
            assertNotNull(null);
        }
    }

    @Test public void testEntryWithInvalidData() {
        try {
            assertNull(new AuthLogEntry(invalidLine));
        } catch(LogParseException ex) {
            assertNull(null);
        }
    }
}
