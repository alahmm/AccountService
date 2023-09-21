package com.example.demo.businesslayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class UserInfoDTO {
        private Long id;
        private String name;
        private String lastname;
        private String email;
        private List<String> roles;
}