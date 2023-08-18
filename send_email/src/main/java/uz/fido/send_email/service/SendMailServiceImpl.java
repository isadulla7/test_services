package uz.fido.send_email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.fido.send_email.model.EmailModel;

@Service
public class SendMailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(EmailModel mailDetail) {
        try {
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setFrom(sender);
            emailMessage.setTo(mailDetail.getReceiver());
            emailMessage.setSubject(mailDetail.getSubject());
            emailMessage.setText(mailDetail.getBody());

            mailSender.send(emailMessage);

            return "Email has been sent successfully...";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
