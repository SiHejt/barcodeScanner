package kirin.barcodescanner.repository;

import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.Entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	 List<Review> findByUserId(long id);
}