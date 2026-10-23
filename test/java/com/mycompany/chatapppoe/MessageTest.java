package com.mycompany.chatapppoe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Message - test data taken from the PROG5121 PoE brief.
 */
public class MessageTest {

    private static final String RECIPIENT_1 = "+27718693002";
    private static final String MESSAGE_1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String RECIPIENT_2 = "08575975889";
    private static final String MESSAGE_2 = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    void setUp() {
        Message.resetAll();
    }

    // ---- message length ----
    @Test
    void messageLengthSuccess() {
        Message m = new Message("0012345678", 0);
        assertEquals("Message ready to send.", m.checkMessageLength(MESSAGE_1));
    }

    @Test
    void messageLengthFailure() {
        Message m = new Message("0012345678", 0);
        String longText = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                m.checkMessageLength(longText));
    }

    // ---- recipient ----
    @Test
    void recipientSuccess() {
        Message m = new Message("0012345678", 0);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell(RECIPIENT_1));
    }

    @Test
    void recipientFailure() {
        Message m = new Message("0112345678", 1);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.", m.checkRecipientCell(RECIPIENT_2));
    }

    // ---- hash ----
    @Test
    void hashTestCase1() {
        Message m = new Message("0012345678", 0);
        assertEquals("00:0:HITONIGHT", m.createMessageHash(MESSAGE_1));
    }

    @Test
    void hashLoopAllMessages() {
        String[] ids = {"0012345678", "0198765432"};
        String[] texts = {MESSAGE_1, MESSAGE_2};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};

        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(ids[i], i);
            assertEquals(expected[i], m.createMessageHash(texts[i]));
        }
    }

    // ---- ID ----
    @Test
    void messageIDCreated() {
        Message m = new Message();
        assertTrue(m.checkMessageID(m.getMessageID()));
        assertEquals(10, m.getMessageID().length());
    }

    // ---- send / discard / store ----
    @Test
    void messageSentOptions() {
        Message m = new Message("0012345678", 0);
        m.checkRecipientCell(RECIPIENT_1);
        m.checkMessageText(MESSAGE_1);
        assertEquals("Message successfully sent.", m.SentMessage(1));
        assertEquals("Press 0 to delete the message.", m.SentMessage(2));
        assertEquals("Message successfully stored.", m.SentMessage(3));
    }

    // ---- total sent ----
    @Test
    void totalMessagesSent() {
        Message m1 = new Message("0012345678", 0);
        m1.checkRecipientCell(RECIPIENT_1);
        m1.checkMessageText(MESSAGE_1);
        Message m2 = new Message("0198765432", 1);
        m2.SentMessage(2); // disregarded
        m1.SentMessage(1); // sent
        assertEquals(1, Message.returnTotalMessagess());
    }
}
