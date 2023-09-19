package com.example.demo.businesslayout;


import com.example.demo.exceptions.CustomBadRequestException;
import com.example.demo.persistence.MyUserRepository;
import com.example.demo.persistence.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private static final Map<String, String> monthMap = new HashMap<>();

    static {
        monthMap.put("01", "January");
        monthMap.put("02", "February");
        monthMap.put("03", "March");
        monthMap.put("04", "April");
        monthMap.put("05", "May");
        monthMap.put("06", "June");
        monthMap.put("07", "July");
        monthMap.put("08", "August");
        monthMap.put("09", "September");
        monthMap.put("10", "October");
        monthMap.put("11", "November");
        monthMap.put("12", "December");
    }
    List<String> keysList = new ArrayList<>(monthMap.keySet());
    @Autowired
    private MyUserRepository employeeRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Transactional
    public ResponseEntity<String> addSalary(List<PaymentDTO> salaryDTOs) {
        for (PaymentDTO paymentDTO : salaryDTOs) {
            String email = paymentDTO.getEmail();
            String period = paymentDTO.getPeriod();
            MyUser user = employeeRepository.findByEmailIgnoreCase(email).get();
            boolean paymentExists = paymentRepository.existsByPeriodAndUser(period, user);
            if (paymentExists) {
                throw new CustomBadRequestException("salary for this month is already added!", "/api/acct/payments");
            } else if (paymentDTO.getSalary() < 0) {
                throw new CustomBadRequestException("salary must be positive!", "/api/acct/payments");
            } else if (!keysList.contains(period.split("-")[0])) {
                throw new CustomBadRequestException("wrong date!", "/api/acct/payments");
            }
            Payment payment = new Payment();
            payment.setUser(employeeRepository.findByEmailIgnoreCase(email).get());
            payment.setSalary(paymentDTO.getSalary());
            payment.setPeriod(paymentDTO.getPeriod());
            paymentRepository.save(payment);
        }
        String status = """
                {
                   "status": "Added successfully!"
                }""";
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<String> updatePayment(PaymentDTO paymentDTO) {
        var employee = employeeRepository.findByEmailIgnoreCase(paymentDTO.getEmail());
        if (employee.isPresent()) {
            String period = paymentDTO.getPeriod();
            if (paymentDTO.getSalary() < 0) {
                throw new CustomBadRequestException("salary must be positive!", "/api/acct/payments");
            } else if (!keysList.contains(period.split("-")[0])) {
                throw new CustomBadRequestException("wrong date!", "/api/acct/payments");
            }
            Payment payment = paymentRepository.findByPeriodAndUser(period, employee.get()).orElseThrow();
            payment.setUser(employee.get());
            payment.setSalary(paymentDTO.getSalary());
            payment.setPeriod(paymentDTO.getPeriod());
            paymentRepository.save(payment);
            String status = """
                {
                   "status": "Updated successfully!"
                }""";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Employee not found");
        }
    }
    public List<EmployeePaymentDTO> getPayments(String email) {
        MyUser user = employeeRepository.findByEmailIgnoreCase(email).orElse(null);

        if (user == null) {
            throw new CustomBadRequestException("User not found", "/api/empl/payment");
        }
        List<Payment> payments = paymentRepository.findByUserOrderByPeriodDesc(user);
        return payments.stream()
                .map(payment -> {
                    EmployeePaymentDTO employeePaymentDTO = new EmployeePaymentDTO();
                    String period = payment.getPeriod();
                    String month = monthMap.get(period.split("-")[0]);
                    employeePaymentDTO.setPeriod(month + "-" + period.split("-")[1]);
                    String salary = String.format("%d dollar(s) %d cent(s)", payment.getSalary() / 100, payment.getSalary() % 100);
                    employeePaymentDTO.setSalary(salary);
                    employeePaymentDTO.setName(user.getName());
                    employeePaymentDTO.setLastname(user.getLastname());
                    return employeePaymentDTO;
                })
                .collect(Collectors.toList());
    }
    public EmployeePaymentDTO getPaymentByPeriod(String email, String period) {
        var user = employeeRepository.findByEmailIgnoreCase(email);
        if (user.isEmpty()) {
            throw new CustomBadRequestException("user not found", " /api/empl/payment");
        }
        var payment = paymentRepository.findByPeriodAndUser(period, user.get());
        if (payment.isEmpty()) {
            throw new CustomBadRequestException("Payment not found for the specified period", "/api/empl/payment");
        }
        EmployeePaymentDTO employeePaymentDTO = new EmployeePaymentDTO();
        String month = monthMap.get(period.split("-")[0]);
        employeePaymentDTO.setPeriod(month + "-" + period.split("-")[1]);
        String salary = String.format("%d dollar(s) %d cent(s)", payment.get().getSalary() / 100, payment.get().getSalary() % 100);
        employeePaymentDTO.setSalary(salary);
        employeePaymentDTO.setName(user.get().getName());
        employeePaymentDTO.setLastname(user.get().getLastname());
        return employeePaymentDTO;
    }
}
