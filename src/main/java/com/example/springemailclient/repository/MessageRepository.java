package com.example.springemailclient.repository;

import com.example.springemailclient.EmailSend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<EmailSend, Long> {
}
