package com.example.demo.businesslayout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@Min(value = 12)
    private String new_password;
    private String email;
    private String status;
}
