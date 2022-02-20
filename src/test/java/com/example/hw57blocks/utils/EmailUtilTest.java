package com.example.hw57blocks.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailUtilTest {

    @Test
    @DisplayName("Email is valid ")
    public void isValidEmail_true(){
        assertTrue(EmailUtil.isValidEmail("edwin.collazos@something.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email is valid with underscore")
    public void isValidEmail_true_underscore(){
        assertTrue(EmailUtil.isValidEmail("edwin_collazos@something.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email is valid with underscore")
    public void isValidEmail_true_underscore2(){
        assertTrue(EmailUtil.isValidEmail("edwincollazos@some_thing.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email invalid because doesn't have the @")
    public void isValidEmail_false() {
        assertFalse(EmailUtil.isValidEmail("edwin.collazossomething.com"), "The email should be invalid");
    }

    @Test
    @DisplayName("Email invalid because doesn't have the .com")
    public void isValidEmail_false_no_dot_com() {
        assertFalse(EmailUtil.isValidEmail("edwin.collazos@somethingcom"), "The email should be invalid");
    }

    @Test
    @DisplayName("Email invalid because has a space")
    public void isValidEmail_false_has_space() {
        assertFalse(EmailUtil.isValidEmail("edwin. collazos@somethingcom"), "The email should be invalid");
    }

    @Test
    @DisplayName("Password is valid")
    public void isValidPassword_true() {
        assertTrue(EmailUtil.isValidPassword("MyPassword!"));
    }

    @Test
    @DisplayName("Password is valid with #")
    public void isValidPassword_true_2() {
        assertTrue(EmailUtil.isValidPassword("MyPassword#"));
    }

    @Test
    @DisplayName("Password is valid with ?")
    public void isValidPassword_true_3() {
        assertTrue(EmailUtil.isValidPassword("MyPassword?"));
    }

    @Test
    @DisplayName("Password is valid with ]")
    public void isValidPassword_true_4() {
        assertTrue(EmailUtil.isValidPassword("MyPassword]"));
    }

    @Test
    @DisplayName("Password is valid with @")
    public void isValidPassword_true_5() {
        assertTrue(EmailUtil.isValidPassword("MyPassword@"));
    }

    @Test
    @DisplayName("Password is invalid since has $")
    public void isValidPassword_false() {
        assertFalse(EmailUtil.isValidPassword("MyPa$sword123"), "The password should be invalid");
    }

    @Test
    @DisplayName("Password is invalid since has empty space")
    public void isValidPassword_false_2() {
        assertFalse(EmailUtil.isValidPassword("MyPa sword123"), "The password should be invalid");
    }
}
