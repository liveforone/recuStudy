package recuStudy.recuStudy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recuStudy.recuStudy.user.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
