package com.tracker.placement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ramchandu438@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Placement Tracker - Verification Code");
            message.setText("Hello,\n\n"
                    + "You requested a password reset. Your 6-digit verification code is:\n\n"
                    + "🔑 " + otpCode + "\n\n"
                    + "This code is valid for the next 5 minutes. If you did not make this request, please ignore this email.\n\n"
                    + "Best regards,\n"
                    + "Placement Tracker Team");
            mailSender.send(message);
            System.out.println("OTP Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send real email: " + e.getMessage());
            System.out.println("=================================================");
            System.out.println("SIMULATION: OTP for " + toEmail + " is: " + otpCode);
            System.out.println("=================================================");
        }
    }
}
