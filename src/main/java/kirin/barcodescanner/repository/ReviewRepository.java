package kirin.barcodescanner.repository;

import kirin.barcodescanner.Entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long id);
    List<Review> findByBarcodeNumber(String barcodeNumber);
}