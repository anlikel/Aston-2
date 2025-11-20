package com.example.serviceinterfaces;

public interface EmailServiceInterface {
    void sendWelcomeEmail(String email, String name);

    void sendAccountDeletionEmail(String email, String name);

    void sendEmail(String to, String subject, String message);
}
