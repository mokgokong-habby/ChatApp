package com.mycompany.chatapppoe;

import java.util.ArrayList;

/**
 * Part 3 - Store Data and Display Task Report.
 *
 * Holds every message in PARALLEL ARRAYS: index 0 of each list describes the
 * same message, index 1 the next message, and so on. Nothing is hard-coded;
 * the lists are filled from user input, the stored JSON file, or (in the unit
 * tests) the developer test data from the brief.
 */
public class MessageManager {

    public static final String FLAG_SENT = "Sent";
    public static final String FLAG_STORED = "Stored";
    public static final String FLAG_DISREGARD = "Disregard";

    // The three arrays required by the brief (message text per category).
    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();

    // Parallel arrays: the same index in each list belongs to the same message.
    private final ArrayList<String> messageIDs = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> senders = new ArrayList<>();
    private final ArrayList<String> recipients = new ArrayList<>();
    private final ArrayList<String> messageTexts = new ArrayList<>();
    private final ArrayList<String> flags = new ArrayList<>();

    /** Adds one message to the parallel arrays and to its category array. */
    public void addMessage(String id, String hash, String sender, String recipient,
                           String text, String flag) {
        messageIDs.add(id);
        messageHashes.add(hash);
        senders.add(sender);
        recipients.add(recipient);
        messageTexts.add(text);
        flags.add(flag);

        if (FLAG_SENT.equals(flag)) {
            sentMessages.add(text);
        } else if (FLAG_STORED.equals(flag)) {
            storedMessages.add(text);
        } else if (FLAG_DISREGARD.equals(flag)) {
            disregardedMessages.add(text);
        }
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<String> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getSenders() {
        return senders;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public ArrayList<String> getMessageTexts() {
        return messageTexts;
    }

    public ArrayList<String> getFlags() {
        return flags;
    }

    /** Returns the longest message across all messages (loop keeps the longest seen so far). */
    public String getLongestMessage() {
        return longestOf(messageTexts);
    }

    /** Returns the longest message in the stored messages array. */
    public String getLongestStoredMessage() {
        return longestOf(storedMessages);
    }

    private String longestOf(ArrayList<String> texts) {
        if (texts.isEmpty()) {
            return "No messages found.";
        }
        String longest = texts.get(0);
        for (String text : texts) {
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        return longest;
    }

    /** Sender and recipient of every stored message, one line per message. */
    public ArrayList<String> getStoredSendersAndRecipients() {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < flags.size(); i++) {
            if (FLAG_STORED.equals(flags.get(i))) {
                lines.add("Sender: " + senders.get(i) + " | Recipient: " + recipients.get(i));
            }
        }
        return lines;
    }

    /** Searches the message ID array and returns the matching message text. */
    public String searchByMessageID(String id) {
        int index = messageIDs.indexOf(id);
        if (index == -1) {
            return "Message ID not found.";
        }
        return messageTexts.get(index);
    }

    /** Same search, but returns the recipient and the message (used by the menu). */
    public String describeMessageByID(String id) {
        int index = messageIDs.indexOf(id);
        if (index == -1) {
            return "Message ID not found.";
        }
        return "Recipient: " + recipients.get(index) + "\nMessage: " + messageTexts.get(index);
    }

    /** All sent or stored messages for one recipient (disregarded messages are excluded). */
    public ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            boolean matches = recipients.get(i).equals(recipient);
            boolean wasKept = !FLAG_DISREGARD.equals(flags.get(i));
            if (matches && wasKept) {
                results.add(messageTexts.get(i));
            }
        }
        return results;
    }

    /** Deletes the message with this hash from every parallel array. */
    public String deleteByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index == -1) {
            return "Message hash not found.";
        }

        String text = messageTexts.get(index);
        String flag = flags.get(index);

        messageIDs.remove(index);
        messageHashes.remove(index);
        senders.remove(index);
        recipients.remove(index);
        messageTexts.remove(index);
        flags.remove(index);

        if (FLAG_SENT.equals(flag)) {
            sentMessages.remove(text);
        } else if (FLAG_STORED.equals(flag)) {
            storedMessages.remove(text);
        } else if (FLAG_DISREGARD.equals(flag)) {
            disregardedMessages.remove(text);
        }

        return "Message: \"" + text + "\" successfully deleted.";
    }

    /** Report listing the hash, recipient and message of every message with the given flag. */
    public String getReport(String flag) {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < flags.size(); i++) {
            if (flag.equals(flags.get(i))) {
                if (report.length() > 0) {
                    report.append("\n\n");
                }
                report.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
                report.append("Recipient: ").append(recipients.get(i)).append("\n");
                report.append("Message: ").append(messageTexts.get(i));
            }
        }
        return report.length() == 0 ? "No messages found." : report.toString();
    }

    public String getSentReport() {
        return getReport(FLAG_SENT);
    }

    public String getStoredReport() {
        return getReport(FLAG_STORED);
    }
}
