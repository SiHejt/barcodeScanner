package kirin.barcodescanner.service;

import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.repository.ReviewRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}