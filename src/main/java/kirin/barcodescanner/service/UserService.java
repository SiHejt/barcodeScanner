package kirin.barcodescanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findOrCreateUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = User.builder().email(email).build();
                return userRepository.save(newUser);
            });
    }
}