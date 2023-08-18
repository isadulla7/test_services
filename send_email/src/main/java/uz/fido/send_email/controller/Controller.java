package uz.fido.send_email.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.fido.send_email.model.EmailModel;
import uz.fido.send_email.service.MailService;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private MailService mailService;

    //Sending email
    @PostMapping("/send_mail")
    public String sendMail(@RequestBody EmailModel mailDetail) {
        return mailService.sendMail(mailDetail);
    }


}
