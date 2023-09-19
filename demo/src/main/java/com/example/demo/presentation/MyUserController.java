package com.example.demo.presentation;

import authexercices.businesslayout.AppUser;
import authexercices.presentation.DemoController;
import com.example.demo.businesslayout.*;
import com.example.demo.exceptions.CustomBadRequestException;
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

import java.util.List;

@Validated
@RestController
public class MyUserController {
    List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");
    @Autowired
    MyUserService myUserService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    PasswordEncoder encoder;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping (path ="/api/auth/signup")
    public ResponseEntity<String> register(@RequestBody @Valid MyUser myUser) throws JsonProcessingException {

        if (!myUserService.ifExistsByEmail(myUser.getEmail())) {
            if (breachedPasswords.contains(myUser.getPassword())) {
                throw new CustomBadRequestException("The password is in the hacker's database!", "/api/auth/signup");
            } else if (myUser.getPassword().length() < 12) {
                throw new CustomBadRequestException("The password length must be at least 12 chars!", "/api/auth/signup");
            }else {
                MyUser myUser1 = new MyUser();

                myUser1.setName(myUser.getName());
                myUser1.setLastname(myUser.getLastname());
                myUser1.setEmail(myUser.getEmail());
                myUser1.setPassword(encoder.encode(myUser.getPassword()));

                MyUser myUser2 = myUserService.saveUser(myUser1);


                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(myUser2), HttpStatus.OK);
            }
        }
        throw new CustomBadRequestException("User exist!", "/api/auth/signup");

    }
    @GetMapping(path = "/api/empl/payment")
    public ResponseEntity<String> getRegisteredInfos(
            @AuthenticationPrincipal UserDetails userDetails1,
            @RequestParam(required = false) String period) throws JsonProcessingException {

        String email = userDetails1.getUsername();
        var myUser = myUserService.findObjectByEmail(email);
        if (myUser.isPresent()) {
            if (period != null) {
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(paymentService.getPaymentByPeriod(email, period)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(paymentService.getPayments(email)), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(path = "api/auth/changepass")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UserPassword userPassword) throws JsonProcessingException {
        if (userDetails == null) {
            // Handle the case where userDetails is null, typically indicating that the user is not authenticated.
            // You can return a 401 Unauthorized response in this case.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String currentUsername = userDetails.getUsername();
        var user = myUserService.findObjectByEmail(currentUsername);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        if (encoder.matches(userPassword.getNew_password(), userDetails.getPassword())) {
            throw new CustomBadRequestException("The passwords must be different!", "/api/auth/changepass");
        } else if (breachedPasswords.contains(userPassword.getNew_password())) {
            throw new CustomBadRequestException("The password is in the hacker's database!", "/api/auth/changepass");
        } else if (userPassword.getNew_password().length() < 12) {
            throw new CustomBadRequestException("Password length must be 12 chars minimum!", "/api/auth/changepass");
        }
        MyUser newUser = user.get();
        newUser.setPassword(encoder.encode(userPassword.getNew_password()));
        userPassword.setEmail(newUser.getEmail().toLowerCase());
        userPassword.setStatus("The password has been updated successfully");
        myUserService.saveUser(newUser);
        return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                writeValueAsString(userPassword), HttpStatus.OK);
    }
    @PostMapping("api/acct/payments")
    public ResponseEntity<String> AddMoreDataAboutUser(@RequestBody List<PaymentDTO> listOfEmployees) {
        return paymentService.addSalary(listOfEmployees);
    }
    @PutMapping("api/acct/payments")
    public ResponseEntity<String> UpdateDataAboutUser(@RequestBody PaymentDTO employee) {
        return paymentService.updatePayment(employee);
    }
    @DeleteMapping("api/delete")
    public ResponseEntity<String> deleter() {
        myUserService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
