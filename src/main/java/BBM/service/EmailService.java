package BBM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String toEmail, String token) {
        // ✅ This is the frontend URL where user will reset the password
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;

        // ✅ Compose email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset Your Password");
        message.setText("Hello,\n\nClick the link below to reset your password:\n" + resetUrl +
                        "\n\nIf you did not request this, please ignore this email.\n\nThank you!");

        // ✅ Send email
        mailSender.send(message);
    }
}
