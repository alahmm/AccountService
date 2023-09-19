package com.example.demo.businesslayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class EmployeePaymentDTO {
    private String name;
    private String lastname;
    private String period;
    private String salary;
}
