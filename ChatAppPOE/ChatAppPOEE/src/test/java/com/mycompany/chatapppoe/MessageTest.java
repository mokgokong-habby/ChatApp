package com.mycompany.chatapppoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Part 2 behaviour: message ID, recipient, length, hash and the sent counter. */
public class MessageTest {

    @BeforeEach
    void setUp() {
        Message.resetTotalMessages();
    }

    @Test
    void testMessageIDIsValid() {
        Message message = new Message("0834557896", 1, "+27834557896", "Did you get the cake?");
        assertTrue(message.checkMessageID());
    }

    @Test
    void testMessageIDTooLong() {
        Message message = new Message("12345678901", 1, "+27834557896", "Hi there");
        assertFalse(message.checkMessageID());
    }

    @Test
    void testRecipientCellCorrectlyFormatted() {
        Message message = new Message("0834557896", 1, "+27834557896", "Hi there");
        assertTrue(message.checkRecipientCell());
    }

    @Test
    void testRecipientCellMissingInternationalCode() {
        Message message = new Message("0834557896", 1, "08966553", "Hi there");
        assertFalse(message.checkRecipientCell());
    }

    @Test
    void testMessageLengthWithinLimit() {
        assertEquals("Message ready to send.", Message.checkMessageLength("Did you get the cake?"));
    }

    @Test
    void testMessageLengthOverLimit() {
        String longText = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10, please reduce size.",
                Message.checkMessageLength(longText));
    }

    @Test
    void testMessageHash() {
        Message message = new Message("0834557896", 1, "+27834557896", "Did you get the cake?");
        assertEquals("08:1:DIDCAKE?", message.getMessageHash());
    }

    @Test
    void testOnlySendingIncreasesTotal() {
        Message message = new Message("0834557896", 1, "+27834557896", "Hi there");
        message.sentMessage("2");
        message.sentMessage("3");
        assertEquals(0, Message.returnTotalMessages());
        message.sentMessage("1");
        assertEquals(1, Message.returnTotalMessages());
    }
}
