package com.example.demo.businesslayout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MyUser {
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String  lastname;

    @NotEmpty
    @Pattern(regexp = ".+@acme.com")
    private String email;

    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnore
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setlastname(String  lastname) {
        this. lastname =  lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getlastname() {
        return  lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public MyUser(String name, String  lastname, String password, String email) {
        this.name = name;
        this. lastname =  lastname;
        this.password = password;
        this.email = email;
    }
    public MyUser() {

    }
}