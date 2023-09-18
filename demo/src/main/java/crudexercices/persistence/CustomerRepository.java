package crudexercices.persistence;

import crudexercices.businesslayout.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findBySubscriptionEndsOn(LocalDate date);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    @Transactional
    void deleteByIsSubscriptionActive(boolean isActive);

    boolean existsByPhoneNumber(String phoneNumber);
    int countByIsSubscriptionActive(boolean isActive);
    List<Customer> findByNameAndSurname(String name, String surname);
    List<Customer> findBySubscriptionEndsOnBetween(LocalDate begin, LocalDate end);
    /**
     * to use it:
     * LocalDate today = LocalDate.now();
     * List<Customer> list1 = repository.findBySubscriptionEndsOnBetween(today, today.plusMonths(1));
     */
    List<Customer> findBySubscriptionEndsOnLessThanEqual(LocalDate date);
    List<Customer> findByOrderBySurname();
    List<Customer> findTop20ByOrderByRegistrationDate();
}
