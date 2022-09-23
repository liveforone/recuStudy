package recuStudy.recuStudy.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recuStudy.recuStudy.study.domain.Study;
import recuStudy.recuStudy.study.dto.StudyDto;
import recuStudy.recuStudy.study.service.StudyService;

import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/study")
    public ResponseEntity<Page<Study>> studyHome(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Study> studyList = studyService.getStudyList(pageable);

        return new ResponseEntity<>(studyList, HttpStatus.OK);
    }

    @GetMapping("/study/search")
    public ResponseEntity<Page<Study>> studySearch(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("keyword") String keyword
    ) {
        Page<Study> searchList = studyService.getSearchList(keyword, pageable);

        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }

    @GetMapping("/study/post")
    public ResponseEntity<?> studyPostPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    문자열 길이(입력받은 월과일)가 한자리일 경우 앞에 0을 붙인다.
    이렇게 설계하면 사용자가 한자릿수의 월과 일을 입력할때
    앞에 0을 붙이던 붙이지 않던 서버에 원하는 값을 저장할 수있다.
     */
    @PostMapping("/study/post")
    public ResponseEntity<?> studyPost(
            @RequestBody StudyDto studyDto,
            Principal principal
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/study"));
        String user = principal.getName();

        studyService.saveStudy(studyDto, user);
        log.info("Study Save Success!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/study/{id}")
    public ResponseEntity<Study> detail(@PathVariable("id") Long id) {
        Study study = studyService.getOne(id);

        return new ResponseEntity<>(study, HttpStatus.OK);
    }
}
