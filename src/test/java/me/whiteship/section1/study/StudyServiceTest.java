package me.whiteship.section1.study;


import me.whiteship.section1.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/* 어노테이션을 사용한다면 반드시 ExtendWith로 Mockito를 추가해야함. */
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    /* (2) 모든 테스트 메서드에서 사용하는 경우 @Mock 어노테이션으로 필드로 선언 */
    // @Mock
    // MemberService memberService;

    // @Mock
    // StudyRepository studyRepository;

    @Test
    /* (3) 해당 메서드에서만 Mock 객체를 받고 싶다면 파라미터로도 가능 */
    void test1 (@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        /* (1) mock static method import */
        // MemberService memberService = mock(MemberService.class);
        // StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

}