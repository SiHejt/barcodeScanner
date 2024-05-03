package kirin.barcodescanner.Entity;

import javax.persistence.*;

import java.time.LocalDate;


@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String barcodeNumber;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDate reviewDate = LocalDate.now(); // LocalDateTime에서 LocalDate로 변경

    @Column(nullable = false)
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReviewDate() { // LocalDateTime에서 LocalDate로 변경된 getter
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) { // LocalDateTime에서 LocalDate로 변경된 setter
        this.reviewDate = reviewDate;
    }
}