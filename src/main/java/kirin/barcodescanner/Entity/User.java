package kirin.barcodescanner.Entity;

import javax.persistence.Id; 
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
    private Long Id;
 
    @Column(nullable = false)
    private String name;
 
    @Column(nullable = false)
    private String email;
 
    @Column
    private String picture;
 
    @Enumerated(EnumType.STRING) // Enum값을 어떤 형태로 저장할지 결정합니다. (기본적은 int)
    @Column(nullable = false)
    private MyRole role; // 사용자의 권한을 관리할 Enum 클래스
 
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