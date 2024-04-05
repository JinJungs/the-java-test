package me.whiteship.section0;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE00")
    public void test() {
        Study study = new Study(-10);
        assertNotNull(study);

        /* assertEquals */
        // assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."); // string 메세지 추가 가능
        // assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."); // supplier 메세지 추가 가능 (필요시에만 실행됨)

        /* assertAll */
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );

        System.out.println("create");
    }

    @Disabled
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE2A") // 이모지 사용 가능
    public void test2() {
        System.out.println("not test");
    }

    @BeforeAll
    static void beforAll() {
        // 테스트 실행전에 한번만 실행
        // static으로 작성
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        // 테스트 실행후에 한번만 실행
        // static으로 작성
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }


}