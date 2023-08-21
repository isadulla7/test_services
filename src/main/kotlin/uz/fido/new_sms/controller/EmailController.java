package uz.fido.new_sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.fido.new_sms.entity.EmailDetails;
import uz.fido.new_sms.service.GmailService;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private GmailService emailService;

    @PostMapping("/gmail")
    public String sendSimpleMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

}
