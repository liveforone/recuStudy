package recuStudy.recuStudy.study.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String user;

    @Column
    private int limitUser;  //제한인원(총 가능 인원)

    @Column
    private int currentUser;  //현재 신청 인원

    private String limitMonth;  //마감 월
    private String limitDay;  //마감 일
    private String location;  //스터디 장소

    @Builder
    public Study(Long id, String title, String content, String user, int limitUser, int currentUser, String limitMonth, String limitDay, String location) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.limitUser = limitUser;
        this.currentUser = currentUser;
        this.limitMonth = limitMonth;
        this.limitDay = limitDay;
        this.location = location;
    }
}
