package com.example.dater.model;

import org.junit.jupiter.api.Test;

import static com.example.dater.model.Settings.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class SettingsTest {
    String emailErrorMsg = "The given email does not match regex expression";
    String phoneErrorMsg = "The given phone number does not match regex expression";

    @Test
    void shouldAcceptEmailAddress() {
        assertTrue(emailErrorMsg, "username@domain.com".matches(EMAIL_REGEX));
        assertTrue(emailErrorMsg, "email@gmail.com".matches(EMAIL_REGEX));
        assertTrue(emailErrorMsg, "test@domain.com".matches(EMAIL_REGEX));
        assertTrue(emailErrorMsg, "lastname@domain.com".matches(EMAIL_REGEX));
        assertTrue(emailErrorMsg, "test.email.with+symbol@domain.com".matches(EMAIL_REGEX));
    }

    @Test
    void shouldRejectEmailAddress() {
        assertFalse(emailErrorMsg, "use@123-com".matches(EMAIL_REGEX));
        assertFalse(emailErrorMsg, "usernamedomain.com".matches(EMAIL_REGEX));
        assertFalse(emailErrorMsg, "username@domaincom".matches(EMAIL_REGEX));
        assertFalse(emailErrorMsg, "A@b@c@domain.com".matches(EMAIL_REGEX));
        assertFalse(emailErrorMsg, "abc\\ is\\”not\\valid@domain.com".matches(EMAIL_REGEX));
        assertFalse(emailErrorMsg, "a”b(c)d,e:f;gi[j\\k]l@domain.com".matches(EMAIL_REGEX));
    }

    @Test
    void shouldAcceptPhoneNumber() {
        assertTrue(phoneErrorMsg, "+372 1234567".matches(PHONE_NR_REGEX));
        assertTrue(phoneErrorMsg, "+123 12345678".matches(PHONE_NR_REGEX));
    }

    @Test
    void shouldRejectPhoneNumber() {
        assertFalse(phoneErrorMsg, "1234567".matches(PHONE_NR_REGEX));
        assertFalse(phoneErrorMsg, "12345678".matches(PHONE_NR_REGEX));
        assertFalse(phoneErrorMsg, "+372 s1234567".matches(PHONE_NR_REGEX));
    }
}
