package com.example.springemailclient;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fromEmail;
    private String toEmail;
    private String body;
    private String subject;
    private String localDateTime;
    @PrePersist
    public void prePersist() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy, h:mm a");
        LocalDateTime local = LocalDateTime.now();
        localDateTime = local.format(formatter);
    }

}
