package recuStudy.recuStudy.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recuStudy.recuStudy.user.domain.Role;
import recuStudy.recuStudy.user.domain.Users;
import recuStudy.recuStudy.user.dto.UserDto;
import recuStudy.recuStudy.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    //== 회원 가입 로직 ==//
    @Transactional
    public Long joinUser(UserDto userDto) {
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setAuth(Role.MEMBER);  //기본 권한 매핑

        return userRepository.save(userDto.toEntity()).getId();
    }

    //== 로그인 - 세션과 컨텍스트홀더 사용 ==//
    @Transactional
    public void login(UserDto userDto, HttpSession httpSession) throws UsernameNotFoundException {

        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Users user = userRepository.findByEmail(email);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(token);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (("admin@company.com").equals(email)) {  //어드민 아이디 지정됨, 비밀번호는 회원가입해야함
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
            userDto.setId(user.getId());
            userDto.setAuth(Role.ADMIN);
            userRepository.save(userDto.toEntity());
        } else if (user.getAuth() == Role.MEMBER) {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        new User(user.getEmail(), user.getPassword(), authorities);
    }

    //== principal과 UserDetailsService를 위한 오버라이드 ==//
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (users.getAuth() == Role.ADMIN) {  //어드민 아이디 지정됨, 비밀번호는 회원가입해야함
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else if (users.getAuth() == Role.MEMBER) {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(users.getEmail(), users.getPassword(), authorities);
    }

    @Transactional
    public void updateStudy(String title, String email) {
        Users users = userRepository.findByEmail(email);

        if (users.getStudy1() == null) {
            userRepository.updateStudy1(title, email);
        } else if (users.getStudy2() == null) {
            userRepository.updateStudy2(title, email);
        } else if (users.getStudy3() == null) {
            userRepository.updateStudy3(title, email);
        }
    }

    //== 유저 정보 가져오기 ==//
    @Transactional(readOnly = true)
    public Users getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
