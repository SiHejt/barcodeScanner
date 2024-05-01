package kirin.barcodescanner.repository;

import kirin.barcodescanner.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}