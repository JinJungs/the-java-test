package me.whiteship.section0;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 이걸 쓰면 테스트 인스턴스를 1개만 만들기 때문에 beforeAll과 afterAll이 static일 필요가 없음 - 대신 value도 + 1 됨, 순서가 필요한 경우는 유용할 수도 있다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudyTest {

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE00")
    void test() {
        Study study = new Study(10);
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

        /* assertThrows */
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        /* assertTimeout */
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });

        // assertTimeoutPreemptively 는 Thread 때문에 주의하여 사용해야함

        System.out.println("create");

    }

    @Disabled
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE2A") // 이모지 사용 가능
    void test2() {
        System.out.println("not test");
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre({JRE.JAVA_16})
    @DisabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    @DisplayName("조건에 따라 테스트 실행하기")
    void test3() {
        String env = System.getenv("TEST_ENV");
        System.out.println(env);

        /* assumeTrue - 참이어야 테스트 실행 */
        assumeTrue("LOCAL".equalsIgnoreCase(env));

        /* assumingThat */
        assumingThat("LOCAL".equalsIgnoreCase(env), () -> {
            System.out.println("test");
        });
    }

    @Test
    @FastTest
    @Order(1)
    void test4() {
        System.out.println("로컬에서 실행됨");
    }

    @SlowTest
    @Order(2)
    void test5() {
        System.out.println("CI환경에서 에서 실행됨"); // ./mvnw test -P ci   로 실행
    }

    @DisplayName("반복 테스트 만들기")
    @Order(3)
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatedTest(RepetitionInfo repetitionInfo) {
        System.out.println("repeated test " + repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("테스트 반복하기 - 1")
    @Order(4)
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있습니다"})
//    @EmptySource
//    @NullSource
    @NullAndEmptySource
    void parameterizedTest(String message){
        System.out.println(message);
    }

    @DisplayName("테스트 반복하기 - 2")
    @Order(5)
    @ParameterizedTest(name = "{index} {displayName} study={0}")
     @ValueSource(ints = {10,20,40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study){ // Integer로 받아도 되지만, 특정 클래스로 받고 싶다면 SimpleArgumentConverter 를 상속한 Converter를 추가해줘야한다.
        System.out.println(study.getLimit());
    }

    @DisplayName("테스트 반복하기 - CsvSource")
    @ParameterizedTest(name = "{index} {displayName} limit={0} name={1}")
    @CsvSource({"10, '자바스터디'", "20, '스프링'"})
    void parameterizedTest3(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }

    // 여러개의
    // inner static class 이거나 퍼블릭 클래스여야 사용가능
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    // 1개의 인자만 가능
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    int value = 1;

    @DisplayName("value ++ 테스트 1")
    @Test
    void testValue() {
        System.out.println("value++ = " + value++);
    }

    @DisplayName("value ++ 테스트 2")
    @Test
    void testValue2() {
        System.out.println("value++ = " + value++);
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