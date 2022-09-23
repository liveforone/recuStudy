package recuStudy.recuStudy.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recuStudy.recuStudy.user.domain.Users;
import recuStudy.recuStudy.user.dto.UserDto;
import recuStudy.recuStudy.user.service.UserService;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //== 메인 페이지 ==//
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 페이지 ==//
    @GetMapping("/user/signup")
    public ResponseEntity<?> signupPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 처리 ==//
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));  //해당 경로로 리다이렉트

        userService.joinUser(userDto);
        log.info("Sign Up Success!!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    //== 로그인 페이지 ==//
    @GetMapping("/user/login")
    public ResponseEntity<?> loginPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그인 ==//
    @PostMapping("/user/login")
    public ResponseEntity<?> loginPage(
            @RequestBody UserDto userDto,
            HttpSession session
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/study"));

        userService.login(userDto, session);
        log.info("Login Success!!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    프론트 단에서 study1, 2, 3중 null 체크를 통해서
    null이 아닌 것들만 내보내고, null인 것은 'null'이라고 내보내지 말고 아예 안내보는 방식으로 진행한다.
    결론적으로 study는 프론트단에서 null체크를 하여서 내보낸다.
     */
    @GetMapping("/user/myPage")
    public ResponseEntity<Users> myPage(Principal principal) {
        String user = principal.getName();
        Users users = userService.getUser(user);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /*
    로그아웃은 시큐리티 단에서 이루어짐.
    /user/logout 으로 post 하면 된다.
     */

    //== 접근 거부 페이지 ==//
    @GetMapping("/user/prohibition")
    public ResponseEntity<?> prohibition() {
        return new ResponseEntity<>("접근 권한이 없습니다. 로그인하여주세요.", HttpStatus.FORBIDDEN);
    }

    //== 어드민 페이지 ==//
    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
