package com.example.demo.persistence;

import com.example.demo.businesslayout.MyUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    boolean existsByEmailIgnoreCase(String email);
    Optional<MyUser> findByEmailIgnoreCase(String email);
    @Transactional
    MyUser save(MyUser myUser);
}
