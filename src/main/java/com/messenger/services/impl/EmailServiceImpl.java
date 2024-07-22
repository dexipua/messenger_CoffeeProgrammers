package com.messenger.services.impl;

import com.messenger.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private String code;
    private void setCode(){
        code = UUID.randomUUID().toString().replace("-", "0").substring(0, 7);
    }
    public void sendEmail(String to) {
        setCode();
        String host = "smtp.office365.com";
        final String username = "abracadbrasome@outlook.com";
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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("VERIFICATION");
            message.setText("Your Verification code is: " + code + ". Dont say it anyone other");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verification(String theirCode){
        if(theirCode.equals(code)){
            return true;
        }else{
            throw new UnsupportedOperationException("Wrong code");
        }
    }
}
