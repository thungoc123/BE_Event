package EventManagement.Event.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            mimeMessage.setContent(content, "text/html");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
