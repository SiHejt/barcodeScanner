package kirin.barcodescanner.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String name;
 
    @Column(nullable = false)
    private String email;
 
    @Column
    private String picture;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MyRole role;
 
    @OneToMany(mappedBy = "user") // User 엔티티와 매핑된 Review 엔티티의 user 필드에 의해 매핑됩니다.
    private List<Review> reviews;
 
    @Builder
    public User(String name, String email, String picture, MyRole role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
 
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
 
        return this;
    }
 
    public String getRoleKey() {
        return this.role.getKey();
    }
}
