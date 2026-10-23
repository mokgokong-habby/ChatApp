package com.mycompany.chatapppoe;

import java.util.Scanner;

/**
 * QuickChat console application - PROG5121 PoE Parts 1 &amp; 2.
 * <p>
 * Flow: Registration -> Login (up to 3 attempts) -> Message menu.
 */
public class ChatAppPOE {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();

            // ================= Part 1: Registration =================
            System.out.println("=== Registration ===");
            System.out.print("Enter a username (contains '_', max 5 characters): ");
            String username = scanner.nextLine();

            System.out.print("Enter a password (8+ chars, capital, number, special char): ");
            String password = scanner.nextLine();

            System.out.print("Enter your South African cell number (e.g. +27838968976): ");
            String cellPhoneNumber = scanner.nextLine();

            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            String registrationMessage = login.registerUser(username, password, cellPhoneNumber, firstName, lastName);
            System.out.println(registrationMessage);
            System.out.println(login.getCellPhoneMessage());

            if (!login.isRegistered()) {
                System.out.println("Registration failed. Please restart the application and try again.");
                return;
            }

            // ================= Part 1: Login =================
            // Users may only send messages after a successful login.
            boolean loggedIn = false;
            int attemptsRemaining = 3;

            System.out.println();
            System.out.println("=== Login ===");
            while (!loggedIn && attemptsRemaining > 0) {
                System.out.print("Enter your username: ");
                String loginUsername = scanner.nextLine();
                System.out.print("Enter your password: ");
                String loginPassword = scanner.nextLine();

                loggedIn = login.loginUser(loginUsername, loginPassword);
                System.out.println(login.getLoginMessage());

                if (!loggedIn) {
                    attemptsRemaining--;
                    if (attemptsRemaining > 0) {
                        System.out.println("Please try again. Attempts remaining: " + attemptsRemaining);
                    }
                }
            }

            if (!loggedIn) {
                System.out.println("Too many failed login attempts. Exiting QuickChat.");
                return;
            }

            // ================= Part 2: Sending Messages =================
            System.out.println();
            System.out.println("Welcome to QuickChat.");

            int numMessages = 0;
            while (numMessages <= 0) {
                System.out.print("How many messages would you like to enter? ");
                try {
                    numMessages = Integer.parseInt(scanner.nextLine().trim());
                    if (numMessages <= 0) {
                        System.out.println("Please enter a number greater than zero.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid whole number.");
                }
            }

            int messagesCreated = 0;
            boolean running = true;

            while (running) {
                System.out.println();
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");
                String option = scanner.nextLine().trim();

                switch (option) {
                    case "1" -> {
                        if (messagesCreated >= numMessages) {
                            System.out.println("You have reached the number of messages you set at the start ("
                                    + numMessages + ").");
                            break;
                        }

                        while (messagesCreated < numMessages) {
                            System.out.println();
                            System.out.println("--- New message (" + (messagesCreated + 1) + " of " + numMessages + ") ---");

                            Message message = new Message();

                            // Message ID check
                            if (!message.checkMessageID(message.getMessageID())) {
                                System.out.println("Message ID is invalid; skipping this message.");
                                continue;
                            }
                            System.out.println("Message ID generated: " + message.getMessageID());

                            // Recipient validation loop
                            String recipientResult;
                            do {
                                System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                                String recipientInput = scanner.nextLine();
                                recipientResult = message.checkRecipientCell(recipientInput);
                                System.out.println(recipientResult);
                            } while (!recipientResult.equals("Cell phone number successfully captured."));

                            // Message text validation loop
                            String messageResult;
                            do {
                                System.out.print("Enter your message (max 250 characters): ");
                                String messageInput = scanner.nextLine();
                                messageResult = message.checkMessageText(messageInput);
                                System.out.println(messageResult);
                            } while (!messageResult.equals("Message sent"));

                            // Send / disregard / store
                            String outcome = message.SentMessage(scanner);
                            System.out.println(outcome);

                            messagesCreated++;

                            if (messagesCreated < numMessages) {
                                System.out.print("Enter another message now? (y/n): ");
                                String again = scanner.nextLine().trim();
                                if (!again.equalsIgnoreCase("y")) {
                                    break;
                                }
                            }
                        }

                        // Total number of messages is displayed once the limit is reached
                        if (messagesCreated >= numMessages) {
                            System.out.println();
                            System.out.println("Total number of messages sent: " + Message.returnTotalMessagess());
                        }
                    }

                    case "2" -> {
                        String log = new Message().printMessages();
                        if (log.isBlank()) {
                            System.out.println("Coming Soon. (No messages have been sent yet.)");
                        } else {
                            System.out.println("--- Recently sent messages ---");
                            System.out.println(log);
                        }
                    }

                    case "3" -> {
                        running = false;
                        System.out.println();
                        System.out.println("Total number of messages sent: " + Message.returnTotalMessagess());
                        System.out.println("Goodbye!");
                    }

                    default -> System.out.println("Please select 1, 2, or 3.");
                }
            }
        }
    }
}
