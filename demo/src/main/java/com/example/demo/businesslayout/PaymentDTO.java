package com.example.demo.businesslayout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @JsonProperty(value = "employee")
    private String email;
    private Long salary;
    private String period;

}
