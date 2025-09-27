package back.domain.user.user.entity;

import back.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호 (암호화 저장)

    @Column(nullable = false)
    private String nickname; // 닉네임

    private String role = "USER"; // 권한 (USER / ADMIN)

    public User (String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public boolean isAdmin(){
        if(role.equals("ADMIN")) return true;
        else return false;
    }
}
