package com.mycompany.chatapppoe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Login - username, password, cell-phone and login flow.
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

    // ---- username ----
    @Test
    void usernameSuccess() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void usernameFailure_noUnderscore() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void usernameFailure_tooLong() {
        assertFalse(login.checkUserName("kyle_1"));
    }

    // ---- password ----
    @Test
    void passwordSuccess() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void passwordFailure() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ---- cell phone ----
    @Test
    void cellPhoneSuccess() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void cellPhoneFailure() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---- registration ----
    @Test
    void registerUserSuccess() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(result.toLowerCase().contains("registered successfully")
                || result.toLowerCase().contains("successfully captured"));
        assertTrue(login.isRegistered());
        assertEquals("Cell phone number successfully captured.", login.getCellPhoneMessage());
    }

    @Test
    void registerUser_badUsername() {
        String result = login.registerUser("kyle", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(result.contains("Username is not correctly formatted"));
        assertFalse(login.isRegistered());
    }

    // ---- login ----
    @Test
    void loginSuccess() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertTrue(login.getLoginMessage().contains("Welcome Kyle, Smith"));
    }

    @Test
    void loginFailure() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(login.loginUser("kyl_1", "wrongPass"));
        assertEquals("Username or password incorrect, please try again.", login.getLoginMessage());
    }
}
