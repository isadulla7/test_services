package uz.fido.new_sms.entity;

// Importing required classes

import lombok.*;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDetails {

    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
