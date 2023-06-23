package com.example.springemailclient;

public record EmailWrapper(
        String from,
        String toEmail,
        String body,
        String subject
) {

}
