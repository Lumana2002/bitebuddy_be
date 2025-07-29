package com.rajan.foodDeliveryApp.services.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetTokenEmail(String recipientEmail, String resetUrl) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject("Password Reset Request");
        email.setText("To reset your password, click the following link: " + resetUrl);
        mailSender.send(email);
    }
}