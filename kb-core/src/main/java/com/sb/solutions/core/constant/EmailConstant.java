package com.sb.solutions.core.constant;

import java.util.Map;

import com.google.common.collect.ImmutableMap;


/**
 * @author Sunil Babu Shrestha on 7/18/2019
 */
public final class EmailConstant {

    public static final Map<Template, String> MAIL = ImmutableMap.<Template, String>builder()
        .put(Template.RESET_PASSWORD, "/mail/password-reset.html")
        .put(Template.ELIGIBILITY_ELIGIBLE, "/mail/eligible.html")
        .put(Template.ACCOUNT_OPENING_THANK_YOU, "/mail/account-opening-thank-you.html")
        .put(Template.ONE_TIME_PASSWORD, "/mail/customer-otp.html")
        .put(Template.TEST, "/mail/email-test.html")
        .put(Template.ACCOUNT_OPENING_ACCEPT, "/mail/account-opening-registration-accept")
        .build();

    private EmailConstant() {
    }


    public enum Template {
        RESET_PASSWORD("Reset Password !!"), ELIGIBILITY_ELIGIBLE("You are Eligible !!"),
        ACCOUNT_OPENING_THANK_YOU("Thank You !! Account Opening request is received."),
        ONE_TIME_PASSWORD("One Time Password"),
        TEST("Email Configuration Test"),
        ACCOUNT_OPENING_ACCEPT("Thank You !! Account Opening request has been accepted.");

        private String subject;

        Template(String subject) {
            this.subject = subject;
        }

        public String get() {
            return this.subject;
        }
    }
}
