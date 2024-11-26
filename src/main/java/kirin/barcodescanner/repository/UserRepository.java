package kirin.barcodescanner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kirin.barcodescanner.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
}