package com.example.demo.persistence;


import com.example.demo.businesslayout.MyUser;
import com.example.demo.businesslayout.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> findByPeriodAndUser(String period, MyUser user);
    List<Payment> findByUserOrderByPeriodDesc(MyUser user);
    boolean existsByPeriodAndUser(String period, MyUser user);
}
