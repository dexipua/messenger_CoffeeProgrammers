package com.messenger.services.interfaces;

public interface EmailService {
    void sendEmail(String to);
    boolean verification(String theirCode);
}
