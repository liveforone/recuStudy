package recuStudy.recuStudy.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recuStudy.recuStudy.study.domain.Study;
import recuStudy.recuStudy.study.dto.StudyDto;
import recuStudy.recuStudy.study.repository.StudyRepository;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    //== find all list ==//
    @Transactional(readOnly = true)
    public Page<Study> getStudyList(Pageable pageable) {
        return studyRepository.findAll(pageable);
    }

    //== study save ==//
    @Transactional
    public void saveStudy(StudyDto studyDto, String user) {
        studyDto.setUser(user);
        studyRepository.save(studyDto.toEntity());
    }
}
