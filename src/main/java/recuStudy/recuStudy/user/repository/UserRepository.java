package recuStudy.recuStudy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recuStudy.recuStudy.user.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    @Modifying
    @Query("update Users u set u.study1 = :title where u.email = :email")
    void updateStudy1(@Param("title") String title, @Param("email") String email);

    @Modifying
    @Query("update Users u set u.study2 = :title where u.email = :email")
    void updateStudy2(@Param("title") String title, @Param("email") String email);

    @Modifying
    @Query("update Users u set u.study3 = :title where u.email = :email")
    void updateStudy3(@Param("title") String title, @Param("email") String email);
}
