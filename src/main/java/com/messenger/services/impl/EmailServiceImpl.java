package com.messenger.services.impl;

import com.messenger.models.VerificationCode;
import com.messenger.services.interfaces.EmailService;
import com.messenger.services.interfaces.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final VerificationCodeService verificationCodeService;
    public void sendEmail(String to) {
        String host = "smtp.office365.com";
        final String username = "coffeeprogrammers@outlook.com";
        final String password = "Some.1234";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            VerificationCode verificationCode = verificationCodeService.generateNewVerificationCode(to);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("VERIFICATION CODE FOR YOU, " + to);
            message.setText("Your code is " + verificationCode.getCode() + ". Secure it!!!");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
