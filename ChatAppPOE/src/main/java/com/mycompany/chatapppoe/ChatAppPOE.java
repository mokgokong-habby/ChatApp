/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppoe;
import java.util.Scanner;
/**
 *
 * @author USER
 */
public class ChatAppPOE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

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

        System.out.println();
        System.out.println("=== Login ===");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        login.loginUser(loginUsername, loginPassword);
        System.out.println(login.getLoginMessage());

        scanner.close();
    }
    
}
