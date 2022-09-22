package recuStudy.recuStudy.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import recuStudy.recuStudy.user.domain.Role;
import recuStudy.recuStudy.user.domain.Users;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private Role auth;
    private String study1;
    private String study2;
    private String study3;

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .auth(auth)
                .study1(study1)
                .study2(study2)
                .study3(study3)
                .build();
    }
}
