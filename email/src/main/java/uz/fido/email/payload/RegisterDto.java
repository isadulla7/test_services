package uz.fido.email.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotNull
    @Size(min = 3,max = 50)
    String firstName;
    @NotNull
    @Size(min = 3,max = 50)
    String Lastname;
    @NotNull
    @Email
    String email;
     @NotNull
    String password;
}
