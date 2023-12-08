package com.baylor.diabeticselfed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInvitationEmail(String to, String token) {
        String subject = "Invitation to Register";
//        String content = "Please use the following link to register: http://localhost:8080/api/register?token=" + token;
        String content = "Please use the following link to register: https://stingray-app-uf6iy.ondigitalocean.app//registration?token=" + token;


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("chysonnet@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
