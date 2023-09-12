package crudexercices.businesslayout;

import crudexercices.presentation.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository1) {
        this.customerRepository = customerRepository1;
    }

    public List<Customer> findCustomer(LocalDate date) {
        return customerRepository.findBySubscriptionEndsOn(date);
    }

}
