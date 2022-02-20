package com.example.hw57blocks.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    @Test
    @DisplayName("Email is valid ")
    public void isValidEmail_true(){
        assertTrue(Util.isValidEmail("edwin.collazos@something.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email is valid with underscore")
    public void isValidEmail_true_underscore(){
        assertTrue(Util.isValidEmail("edwin_collazos@something.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email is valid with underscore")
    public void isValidEmail_true_underscore2(){
        assertTrue(Util.isValidEmail("edwincollazos@some_thing.com"), "The email should be valid");
    }

    @Test
    @DisplayName("Email invalid because doesn't have the @")
    public void isValidEmail_false() {
        assertFalse(Util.isValidEmail("edwin.collazossomething.com"), "The email should be invalid");
    }

    @Test
    @DisplayName("Email invalid because doesn't have the .com")
    public void isValidEmail_false_no_dot_com() {
        assertFalse(Util.isValidEmail("edwin.collazos@somethingcom"), "The email should be invalid");
    }

    @Test
    @DisplayName("Email invalid because has a space")
    public void isValidEmail_false_has_space() {
        assertFalse(Util.isValidEmail("edwin. collazos@somethingcom"), "The email should be invalid");
    }

    @Test
    @DisplayName("Password is valid")
    public void isValidPassword_true() {
        assertTrue(Util.isValidPassword("MyPassword!"));
    }

    @Test
    @DisplayName("Password is valid with #")
    public void isValidPassword_true_2() {
        assertTrue(Util.isValidPassword("MyPassword#"));
    }

    @Test
    @DisplayName("Password is valid with ?")
    public void isValidPassword_true_3() {
        assertTrue(Util.isValidPassword("MyPassword?"));
    }

    @Test
    @DisplayName("Password is valid with ]")
    public void isValidPassword_true_4() {
        assertTrue(Util.isValidPassword("MyPassword]"));
    }

    @Test
    @DisplayName("Password is valid with @")
    public void isValidPassword_true_5() {
        assertTrue(Util.isValidPassword("MyPassword@"));
    }

    @Test
    @DisplayName("Password is invalid since has $")
    public void isValidPassword_false() {
        assertFalse(Util.isValidPassword("MyPa$sword123"), "The password should be invalid");
    }

    @Test
    @DisplayName("Password is invalid since has empty space")
    public void isValidPassword_false_2() {
        assertFalse(Util.isValidPassword("MyPa sword123"), "The password should be invalid");
    }
}
