package uz.fido.send_email.service;

import uz.fido.send_email.model.EmailModel;

public interface MailService {
    String sendMail(EmailModel mailDetail);
}
