package me.whiteship.section1.study;


import me.whiteship.section1.domain.Member;
import me.whiteship.section1.domain.Study;
import me.whiteship.section1.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.input.WhitespaceNormalizedSource;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    void test2(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("justice@gmail.com");

        /* Stubbing */
        /* do는 매개변수가 없는 메서드 */
        doThrow(new IllegalArgumentException()).when(memberService).validate(2L);

        assertThrows(IllegalArgumentException.class, () -> {
           memberService.validate(2L);
        });

        /* when 은 매개변수가 있는 메서드 */
        /* 메소드가 동일한 매개변수로 여러번 호출될 때 각기 다르게 행동호도록 조작할 수도 있다.*/
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> findById = memberService.findById(1L);
        assertEquals("justice@gmail.com", findById.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            Optional<Member> findById2 = memberService.findById(1L);
        });

        Optional<Member> findById3 = memberService.findById(1L);
        assertEquals(Optional.empty(), findById3);

    }

    @Test
    void test3(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("justice@gmail.com");

        Study study = new Study(10, "테스트");

        /* quiz */
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member, study.getOwner());

        /* 결과를 확인할 수 없는경우, 호출 횟수 등을 체크할 수 있다. */
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        /* 호출 순서 확인 */
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        /* 더 이상 호출하지 않는다. */
        verifyNoMoreInteractions(memberService);

    }

}