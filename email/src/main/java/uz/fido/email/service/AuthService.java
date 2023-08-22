package uz.fido.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.fido.email.controller.RoleRepository;
import uz.fido.email.entity.User;
import uz.fido.email.entity.emun.RoleName;
import uz.fido.email.payload.ApiResponse;
import uz.fido.email.payload.RegisterDto;
import uz.fido.email.repository.UserRepository;
import uz.fido.email.security.SecurityConfig;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

      @Autowired
    UserRepository userRepository;

      @Autowired
    PasswordEncoder passwordEncoder;

      @Autowired
    RoleRepository roleRepository;

      @Autowired
    JavaMailSender javaMailSender;
    public ApiResponse addRegister(RegisterDto registerDto){
        User user=new User();
        boolean emailExist= userRepository.existsByEmail(registerDto.getEmail());
        if (emailExist){
            return new ApiResponse("bunday user mavjud",false);
        }
        user.setFirstName(registerDto.getFirstName());
        user.setLastname(registerDto.getLastname());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        Boolean aBoolean = sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Ruyxatdan utdingiz",true);
    }


    public Boolean sendEmail(String sendingEmail,String emailCode){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("jamshideshboboyev@gmail.com");
            mailMessage.setTo(sendingEmail.toString());
            mailMessage.setSubject("Xabarni tasdiqlang");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailcode="+emailCode+"+&email="+emailCode+"'>tastiqlash code</a>");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
