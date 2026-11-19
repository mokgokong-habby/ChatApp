package com.mycompany.chatapppoe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Part 3 - Reads and writes the stored messages as a JSON file.
 *
 * The file is a JSON array of objects, e.g.
 * [ { "messageID": "...", "messageHash": "...", "sender": "...",
 *     "recipient": "...", "message": "...", "flag": "Stored" } ]
 *
 * Reference: JSON syntax follows the specification at https://www.json.org
 * (strings, objects, arrays and backslash escapes). A small hand-written
 * parser is used so the project needs no external library.
 */
public class MessageStorage {

    /** Writes every message flagged "Stored" in the manager to the JSON file. */
    public static void saveStoredMessages(String filePath, MessageManager manager) throws IOException {
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < manager.getFlags().size(); i++) {
            if (!MessageManager.FLAG_STORED.equals(manager.getFlags().get(i))) {
                continue;
            }
            if (!first) {
                json.append(",");
            }
            first = false;
            json.append("\n  {");
            json.append("\"messageID\": ").append(quote(manager.getMessageIDs().get(i))).append(", ");
            json.append("\"messageHash\": ").append(quote(manager.getMessageHashes().get(i))).append(", ");
            json.append("\"sender\": ").append(quote(manager.getSenders().get(i))).append(", ");
            json.append("\"recipient\": ").append(quote(manager.getRecipients().get(i))).append(", ");
            json.append("\"message\": ").append(quote(manager.getMessageTexts().get(i))).append(", ");
            json.append("\"flag\": ").append(quote(manager.getFlags().get(i)));
            json.append("}");
        }
        json.append(first ? "]" : "\n]");
        Files.write(Paths.get(filePath), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    /** Reads the JSON file and adds every message in it to the manager's arrays. */
    public static void loadStoredMessages(String filePath, MessageManager manager) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return; // nothing stored yet
        }
        String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        if (json.trim().isEmpty()) {
            return;
        }
        for (Map<String, String> entry : parse(json)) {
            manager.addMessage(
                    entry.getOrDefault("messageID", ""),
                    entry.getOrDefault("messageHash", ""),
                    entry.getOrDefault("sender", ""),
                    entry.getOrDefault("recipient", ""),
                    entry.getOrDefault("message", ""),
                    entry.getOrDefault("flag", MessageManager.FLAG_STORED));
        }
    }

    // ------------------------------------------------------------------
    // Minimal JSON helpers
    // ------------------------------------------------------------------

    private static String quote(String value) {
        StringBuilder out = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"':
                    out.append("\\\"");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                default:
                    out.append(c);
            }
        }
        return out.append("\"").toString();
    }

    // Parser state: the text being read and the current position in it.
    private static String text;
    private static int pos;

    /** Parses a JSON array of flat objects whose values are all strings. */
    static List<Map<String, String>> parse(String json) {
        text = json;
        pos = 0;
        List<Map<String, String>> entries = new ArrayList<>();

        skipWhitespace();
        expect('[');
        skipWhitespace();
        if (peek() == ']') {
            pos++;
            return entries;
        }
        while (true) {
            skipWhitespace();
            entries.add(parseObject());
            skipWhitespace();
            char next = text.charAt(pos++);
            if (next == ']') {
                break;
            }
            if (next != ',') {
                throw new IllegalArgumentException("Invalid JSON: expected ',' or ']' at " + pos);
            }
        }
        return entries;
    }

    private static Map<String, String> parseObject() {
        Map<String, String> entry = new LinkedHashMap<>();
        expect('{');
        skipWhitespace();
        if (peek() == '}') {
            pos++;
            return entry;
        }
        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            expect(':');
            skipWhitespace();
            String value = parseString();
            entry.put(key, value);
            skipWhitespace();
            char next = text.charAt(pos++);
            if (next == '}') {
                break;
            }
            if (next != ',') {
                throw new IllegalArgumentException("Invalid JSON: expected ',' or '}' at " + pos);
            }
        }
        return entry;
    }

    private static String parseString() {
        expect('"');
        StringBuilder out = new StringBuilder();
        while (true) {
            char c = text.charAt(pos++);
            if (c == '"') {
                break;
            }
            if (c == '\\') {
                char escaped = text.charAt(pos++);
                switch (escaped) {
                    case 'n':
                        out.append('\n');
                        break;
                    case 'r':
                        out.append('\r');
                        break;
                    case 't':
                        out.append('\t');
                        break;
                    case 'b':
                        out.append('\b');
                        break;
                    case 'f':
                        out.append('\f');
                        break;
                    case 'u':
                        out.append((char) Integer.parseInt(text.substring(pos, pos + 4), 16));
                        pos += 4;
                        break;
                    default:
                        out.append(escaped); // covers \" \\ and \/
                }
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    private static void skipWhitespace() {
        while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
            pos++;
        }
    }

    private static char peek() {
        return text.charAt(pos);
    }

    private static void expect(char expected) {
        if (pos >= text.length() || text.charAt(pos) != expected) {
            throw new IllegalArgumentException("Invalid JSON: expected '" + expected + "' at " + pos);
        }
        pos++;
    }
}
