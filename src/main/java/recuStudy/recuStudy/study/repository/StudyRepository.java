package recuStudy.recuStudy.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recuStudy.recuStudy.study.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
