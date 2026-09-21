/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapppoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests using the exact test data specified in the task brief.
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

  
    @Test
    void testUsernameCorrectlyFormatted() {
        login.registerUser("kyl_1", "H@bby#74!Qm9$Lx", "+27838968976", "Kyle", "Naidoo");
        login.loginUser("kyl_1", "H@bby#74!Qm9$Lx");
        assertEquals("Welcome Kyle, Naidoo it is great to see you.", login.getLoginMessage());
    }

  
    @Test
    void testUsernameIncorrectlyFormatted() {
        login.checkUserName("kyle!!!!!!");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.getUsernameMessage());
    }

   
    @Test
    void testPasswordMeetsComplexity() {
        login.checkPasswordComplexity("H@bby#74!Qm9$Lx");
        assertEquals("Password successfully captured.", login.getPasswordMessage());
    }

  
    @Test
    void testPasswordDoesNotMeetComplexity() {
        login.checkPasswordComplexity("password");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.getPasswordMessage());
    }

  
    @Test
    void testCellPhoneCorrectlyFormatted() {
        login.checkCellPhoneNumber("+27838968976");
        assertEquals("Cell number successfully captured.", login.getCellPhoneMessage());
    }

   
    @Test
    void testCellPhoneIncorrectlyFormatted() {
        login.checkCellPhoneNumber("08966553");
        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.", login.getCellPhoneMessage());
    }

  

    @Test
    void testLoginSuccessful() {
        login.registerUser("kyl_1", "H@bby#74!Qm9$Lx", "+27838968976", "Kyle", "Habby");
        assertTrue(login.loginUser("kyl_1", "H@bby#74!Qm9$Lx"));
    }

    @Test
    void testLoginFailed() {
        login.registerUser("kyl_1", "H@bby#74!Qm9$Lx", "+27838968976", "Kyle", "Habby");
        assertFalse(login.loginUser("kyl_1", "WrongPassword1!"));
    }

    @Test
    void testUsernameCorrectlyFormattedTrue() {
        assertTrue(login.checkUserName("Habby"));
    }

    @Test
    void testUsernameIncorrectlyFormattedFalse() {
        assertFalse(login.checkUserName("Habby!!!!!!"));
    }

    @Test
    void testPasswordMeetsComplexityTrue() {
        assertTrue(login.checkPasswordComplexity("H@bby#74!Qm9$Lx!"));
    }

    @Test
    void testPasswordDoesNotMeetComplexityFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void testCellPhoneCorrectlyFormattedTrue() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testCellPhoneIncorrectlyFormattedFalse() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}
    
    

