package com.example.demo.businesslayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequest {

    private String user;
    private String role;
    private String operation;

}
