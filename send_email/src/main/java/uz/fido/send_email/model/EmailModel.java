package uz.fido.send_email.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailModel {
    private String receiver;
    private String subject;
    private String body;
}
