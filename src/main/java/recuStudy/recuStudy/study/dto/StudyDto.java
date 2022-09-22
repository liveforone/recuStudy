package recuStudy.recuStudy.study.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import recuStudy.recuStudy.study.domain.Study;

@Data
@NoArgsConstructor
public class StudyDto {

    private Long id;
    private String title;
    private String content;
    private String user;
    private int limitUser;
    private int currentUser;
    private String limitMonth;
    private String limitDay;
    private String location;

    public Study toEntity() {
        return Study.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user)
                .limitUser(limitUser)
                .currentUser(currentUser)
                .limitMonth(limitMonth)
                .limitDay(limitDay)
                .location(location)
                .build();
    }
}
