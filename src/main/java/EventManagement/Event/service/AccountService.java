package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.repository.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JavaMailSender mailSender;
    public Account getAccountByEmail(String email) {return accountRepository.findByEmail(email);}
    public Account getAccountById(int id) {return accountRepository.findById(id);}
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void disableAccount(int id) {
        Account account = accountRepository.findById(id);
        if (account != null) {
            account.setEnabled(false);
            accountRepository.save(account);
        }
    }
    public void enableAccount(int id) {
        Account account = accountRepository.findById(id);
        if (account != null) {
            account.setEnabled(true);
            accountRepository.save(account);
            sendActivationEmail(account);
        }
    }
    private void sendActivationEmail(Account account) {
        String subject = "Your account has been activated";
        String body = "Hello " + account.getEmail() + ",\n\nYour account has been successfully activated. You can now log in and use our services.\n\nBest regards,\nYour Company";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(account.getEmail());
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }


    public void generateResetToken(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            String token = UUID.randomUUID().toString();
            account.setResetToken(token);
            accountRepository.save(account);
            sendResetTokenEmail(account, token);
        }
    }

    private void sendResetTokenEmail(Account account, String token) {
        String resetUrl = "" + token;
        String subject = "Reset your password";
        String body = "Click the link below to reset your password:\n" + resetUrl;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(account.getEmail());
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

    public void resetPassword(String token, String newPassword) {
        Account account = accountRepository.findByResetToken(token);
        if (account != null) {
            account.setPassword(newPassword); // Không mã hóa mật khẩu
            account.setResetToken(null);
            accountRepository.save(account);
        } else {
            throw new RuntimeException("Invalid reset token");
        }
    }
}



