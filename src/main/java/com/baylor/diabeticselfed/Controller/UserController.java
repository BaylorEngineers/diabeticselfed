package com.baylor.diabeticselfed.Controller;
import com.baylor.diabeticselfed.Response.ResponseMessage;

import com.baylor.diabeticselfed.DTO.LoginRequestDTO;
import com.baylor.diabeticselfed.DTO.RegistrationRequestDTO;
import com.baylor.diabeticselfed.Entity.User;
import com.baylor.diabeticselfed.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = encoder.matches(loginRequest.getPassword(), user.getPassword());

        if (user != null && isPasswordMatch) {
            return ResponseEntity.ok(user.getRole());
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequestDTO registrationDTO) {
        if (userService.existsByUsername(registrationDTO.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Error: Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(registrationDTO.getPassword());

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(hashedPassword); 
        user.setRole(registrationDTO.getRole());

        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

}
