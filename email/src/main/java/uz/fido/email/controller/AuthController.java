package uz.fido.email.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.fido.email.payload.ApiResponse;
import uz.fido.email.payload.RegisterDto;
import uz.fido.email.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    AuthService authService;

    @PostMapping("/login")
    HttpEntity<?> addREgister(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.addRegister(registerDto);
        return ResponseEntity.status(apiResponse.success?201:404).body(apiResponse);

    }
}
