package com.sb.solutions.core.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;


/**
 * @author Sunil Babu Shrestha on 7/18/2019
 */
public final class EmailConstant {

    public static final Map<Template, String> MAIL = ImmutableMap.<Template, String>builder()
            .put(Template.RESET_PASSWORD, "/mail/password-reset.html")
            .put(Template.ELIGIBILITY_ELIGIBLE, "/mail/eligible.html")
            .put(Template.ACCOUNT_OPENING_THANK_YOU, "/mail/account-opening-thank-you.html")
            .build();

    private EmailConstant() {
    }


    public enum Template {
        RESET_PASSWORD("Reset Password !!"), ELIGIBILITY_ELIGIBLE("You are Eligible !!"),
        ACCOUNT_OPENING_THANK_YOU("Thank You !! Account Opening request is received.");

        private String subject;

        Template(String subject) {
            this.subject = subject;
        }

        public String get() {
            return this.subject;
        }
    }
}
