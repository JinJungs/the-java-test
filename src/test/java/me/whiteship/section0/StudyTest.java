package me.whiteship.section0;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE00")
    public void test() {
        Study study = new Study();
        assertNotNull(study);
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