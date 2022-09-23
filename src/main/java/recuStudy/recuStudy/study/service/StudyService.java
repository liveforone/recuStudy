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
    /*
    월과 일이 한자릿수 일경우 앞에 0을 붙이고 아닐경우 그대로 저장한다.
     */
    @Transactional
    public void saveStudy(StudyDto studyDto, String user) {
        if (studyDto.getLimitMonth().length() == 1) {
            studyDto.setLimitMonth("0" + studyDto.getLimitMonth());
        }
        if (studyDto.getLimitDay().length() == 1) {
            studyDto.setLimitDay("0" + studyDto.getLimitDay());
        }
        studyDto.setUser(user);
        studyRepository.save(studyDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Study getOne(Long id) {
        return studyRepository.findOneById(id);
    }

    @Transactional(readOnly = true)
    public Page<Study> getSearchList(String keyword, Pageable pageable) {
        return studyRepository.findByTitleContaining(keyword, pageable);
    }

    //== 현재 참여인원 +1 ==//
    @Transactional
    public void updateCurrUser(Long id) {
        studyRepository.updateCurrUser(id);
    }
}
