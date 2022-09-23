package recuStudy.recuStudy.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role auth;

    private String study1;
    private String study2;
    private String study3;

    @Builder
    public Users(Long id, String email, String password, Role auth, String study1, String study2, String study3) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.study1 = study1;
        this.study2 = study2;
        this.study3 = study3;
    }
}
