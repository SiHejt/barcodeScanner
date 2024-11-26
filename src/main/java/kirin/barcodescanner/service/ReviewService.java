package kirin.barcodescanner.service;

import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findReviewsByUserId(long id) {
        return reviewRepository.findByUserId(id);
    }

    public List<Review> findReviewsByBarcodeNumber(String barcodeNumber) {
        return reviewRepository.findByBarcodeNumber(barcodeNumber);
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}