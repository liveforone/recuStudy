package recuStudy.recuStudy.studyApply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import recuStudy.recuStudy.study.domain.Study;
import recuStudy.recuStudy.study.service.StudyService;
import recuStudy.recuStudy.user.service.UserService;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyApplyController {  //스터디 신청 컨트롤러

    private final StudyService studyService;
    private final UserService userService;

    /*
    인원제한과 마감제한
     */
    @PostMapping("/study/apply/{id}")
    public ResponseEntity<?> studyApply(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        String url = "/study/" + id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(url));

        String currUser = principal.getName();
        LocalDate localDate = LocalDate.now();
        String parsedLocalDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] arr = parsedLocalDate.split("-");
        int currMonth = Integer.parseInt(arr[1]);
        int currDay = Integer.parseInt(arr[2]);
        Study study = studyService.getOne(id);
        int limitMon = Integer.parseInt(study.getLimitMonth());
        int limitDay = Integer.parseInt(study.getLimitDay());
        String title = study.getTitle();

        if (study.getCurrUser() == study.getLimitUser()) {  //인원이 다 찬 경우
            log.info("지원이 거부됨");
            return new ResponseEntity<>("인원이 다 차서 지원이 불가능합니다.", HttpStatus.FORBIDDEN);
        } else {  //마감일 다 찬 경우
            if (limitMon <= currMonth && limitDay < currDay) {  //마감 월과 일을 넘겼을때
                return new ResponseEntity<>("마감 날짜가 지났습니다.", HttpStatus.PRECONDITION_FAILED);
            } else {  //지원
                studyService.updateCurrUser(id);
                userService.updateStudy(title, currUser);
                log.info("지원 요청 성공");
                return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
            }
        }
    }
}
