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
        final String username = "coffeeprogrammersv2@outlook.com";
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
            message.setSubject("Email verification");


            String htmlContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
                    + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border: 1px solid #dddddd; }"
                    + "h2 { color: #333333; }"
                    + "p { font-size: 16px; }"
                    + "strong { display: inline-block; padding: 10px 20px; background-color: #007bff; color: #ffffff; border-radius: 5px; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<h2>Email Verification for CoffeeProgrammers</h2>"
                    + "<p>Dear User,</p>"
                    + "<p>Thank you for joining to CoffeeProgrammers Messenger! Copy the code below to verify your email address:</p>"
                    + "<p><strong>      " + verificationCode.getCode() + "</strong></p>"
                    + "<p>If you did not expect this verification, just ignore this email. Your account will not be activated until you verify your email address.</p>"
                    + "<p>Thank you,<br>The CoffeeProgrammers Team</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            String unsubscribeHeader = "<mailto:coffeeprogrammersv2@outlook.com?subject=Unsubscribe>";
            message.setHeader("List-Unsubscribe", unsubscribeHeader);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
