package uz.fido.new_sms.service;



// Interface

import uz.fido.new_sms.entity.EmailDetails;

public interface GmailService {
        String sendSimpleMail(EmailDetails details);


}
