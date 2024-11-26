package kirin.barcodescanner.Entity;

import javax.persistence.*;

@Entity
public class LikedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String barcodeNumber;

    // Default constructor
    public LikedItem() {}

    // Constructor with fields
    public LikedItem(User user, String barcodeNumber) {
        this.user = user;
        this.barcodeNumber = barcodeNumber;
    }
    
    // getters and setters
    
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }
}