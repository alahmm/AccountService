package com.example.demo.presentation;

import authexercices.businesslayout.AppUser;
import authexercices.presentation.DemoController;
import com.example.demo.businesslayout.MyUser;
import com.example.demo.businesslayout.MyUserService;
import com.example.demo.exceptions.UserExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class MyUserController {
    @Autowired
    MyUserService myUserService;
    @Autowired
    PasswordEncoder encoder;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping (path ="/api/auth/signup")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest myUser) throws JsonProcessingException {
       if (!myUserService.ifExistsByEmail(myUser.email)) {
           MyUser myUser1 = new MyUser();

           myUser1.setName(myUser.name);
           myUser1.setLastname(myUser.lastname);
           myUser1.setEmail(myUser.email);
           myUser1.setPassword(encoder.encode(myUser.password));

           MyUser myUser2 = myUserService.saveUser(myUser1);

           return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                   writeValueAsString(myUser2), HttpStatus.OK);
       }
        throw new UserExistException();
    }
    record RegistrationRequest(String name, String lastname, String email, String password) { }

    @GetMapping(path ="api/empl/payment")
    public ResponseEntity<String> getRegisterdInfos(@AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        String email = userDetails.getUsername();
        var myUser = myUserService.findObjectByEmail(email);
            if (myUser.isPresent()) {
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(myUser.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("api/delete")
    public ResponseEntity<String> deleter() {
        myUserService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
