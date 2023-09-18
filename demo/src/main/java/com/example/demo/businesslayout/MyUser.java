package com.example.demo.businesslayout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue
    private long id;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String authority;



    public MyUser(String name, String  lastname, String password, String email) {
        this.name = name;
        this. lastname =  lastname;
        this.password = password;
        this.email = email;
    }

}