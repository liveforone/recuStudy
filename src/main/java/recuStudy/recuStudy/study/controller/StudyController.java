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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/study/post")
    public ResponseEntity<?> studyPostPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
}
