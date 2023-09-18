package com.example.demo.businesslayout;

import com.example.demo.persistence.MyUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MyUserService {

    private final MyUserRepository myUserRepository;

    @Autowired
    public MyUserService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public boolean ifExistsByEmail(String email) {
        return myUserRepository.existsByEmailIgnoreCase(email);
    }
    public MyUser saveUser(MyUser myUser) {
        return  myUserRepository.save(myUser);
    }
    public Optional<MyUser> findObjectByEmail(String email) {
        return myUserRepository.findByEmailIgnoreCase(email);
    }
    public List<MyUser> getUsers() {

        return myUserRepository.findAll();
    }
    public void delete() {
        myUserRepository.deleteAll();

    }

}
