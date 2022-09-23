package recuStudy.recuStudy.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recuStudy.recuStudy.study.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Study findOneById(Long id);

    Page<Study> findByTitleContaining(String keyword, Pageable pageable);

    //== 현재 참여인원 +1 ==//
    @Modifying
    @Query("update Study s set s.currUser = s.currUser + 1 where s.id = :id")
    void updateCurrUser(@Param("id") Long id);
}
