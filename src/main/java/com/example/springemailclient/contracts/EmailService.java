package com.example.springemailclient.contracts;

import com.example.springemailclient.EmailWrapper;
import com.example.springemailclient.wrapper.UniversalResponse;
import jakarta.mail.MessagingException;
import reactor.core.publisher.Mono;

import java.io.File;

public interface EmailService {
    Mono<UniversalResponse> sendEmail(EmailWrapper emailWrapper);
    Mono<UniversalResponse> sendEmailWithAttachment(EmailWrapper emailWrapper, File file) throws MessagingException;
}
