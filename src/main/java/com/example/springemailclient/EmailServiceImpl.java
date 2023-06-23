package com.example.springemailclient;

import com.example.springemailclient.contracts.EmailService;
import com.example.springemailclient.repository.MessageRepository;
import com.example.springemailclient.wrapper.UniversalResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final MessageRepository messageRepository;
    @Override
    public Mono<UniversalResponse> sendEmail(EmailWrapper emailWrapper) {
        return Mono.fromCallable(()  -> {
            log.info("creating a Simple emailSend object");
            SimpleMailMessage message =  new SimpleMailMessage();
            message.setFrom(emailWrapper.from());
            message.setTo(emailWrapper.toEmail());
            message.setSubject(emailWrapper.subject());
            message.setText(emailWrapper.body());
            javaMailSender.send(message);
            log.info("message sent");
            EmailSend emailSend = EmailSend.builder()
                     .fromEmail(emailWrapper.from())
                     .toEmail(emailWrapper.toEmail())
                     .subject(emailWrapper.subject())
                     .body(emailWrapper.body())
                    .build();
            messageRepository.save(emailSend);
            log.info("Email sent");
            return UniversalResponse.builder()
                    .status(201)
                    .data(emailSend)
                    .message("Email sent successfully")
                    .build();
        }).publishOn(Schedulers.boundedElastic());



    }
    @Override
    public Mono<UniversalResponse> sendEmailWithAttachment(EmailWrapper emailWrapper, File file) throws MessagingException {
        return Mono.fromCallable(()  -> {
            log.info("Send email with attachment");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(emailWrapper.from());
            mimeMessageHelper.setTo(emailWrapper.toEmail());
            mimeMessageHelper.addAttachment(file.getName(), file);
            javaMailSender.send(mimeMessage);
            log.info("Mail sent successfully");
            return UniversalResponse.builder()
                    .status(201)
                    .message("created Message with Attachment")
                    .data(mimeMessage).build();
        }).publishOn(Schedulers.boundedElastic());
       }
}
