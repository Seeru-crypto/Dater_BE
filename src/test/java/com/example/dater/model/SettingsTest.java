package com.example.dater.model;

import org.junit.jupiter.api.Test;

import static com.example.dater.model.Settings.EMAIL_REGEX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class SettingsTest {

    String errorMsg = "The given email does not match regex expression";

    @Test
    void shoulAcceptEmailAddress() {
        assertTrue(errorMsg, "username@domain.com".matches(EMAIL_REGEX));
        assertTrue(errorMsg, "email@gmail.com".matches(EMAIL_REGEX));
        assertTrue(errorMsg, "test@domain.com".matches(EMAIL_REGEX));
        assertTrue(errorMsg, "lastname@domain.com".matches(EMAIL_REGEX));
        assertTrue(errorMsg, "test.email.with+symbol@domain.com".matches(EMAIL_REGEX));
    }

    @Test
    void shouldRejectEmailAddress() {
        assertFalse(errorMsg, "use@123-com".matches(EMAIL_REGEX));
        assertFalse(errorMsg, "usernamedomain.com".matches(EMAIL_REGEX));
        assertFalse(errorMsg, "username@domaincom".matches(EMAIL_REGEX));
        assertFalse(errorMsg, "A@b@c@domain.com".matches(EMAIL_REGEX));
        assertFalse(errorMsg, "abc\\ is\\”not\\valid@domain.com".matches(EMAIL_REGEX));
        assertFalse(errorMsg, "a”b(c)d,e:f;gi[j\\k]l@domain.com".matches(EMAIL_REGEX));
    }
}
