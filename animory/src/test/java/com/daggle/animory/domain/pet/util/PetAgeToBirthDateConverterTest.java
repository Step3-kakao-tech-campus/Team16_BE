package com.daggle.animory.domain.pet.util;

import autoparams.AutoSource;
import autoparams.Repeat;
import com.daggle.animory.common.error.exception.BadRequest400;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
class PetAgeToBirthDateConverterTest {

    @Nested
    class 수동_age문자열입력_테스트 {
        @ParameterizedTest
        @CsvSource(textBlock = """
            0년0개월,0,0
            0년1개월,0,1
            3년2개월,3,2
            """)
        void 성공_생일계산테스트(final String age, final int year, final int month) {

            final LocalDate birthDate = PetAgeToBirthDateConverter.ageToBirthDate(age);

            assertEqualsBirthDate(birthDate, year, month);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1년 2개월", "-3년4개월", "2 년1개월", "1년12 개월", "3년4개 월"})
        void 실패_생일계산테스트_문자열내부공백(final String age) {
            assertThatThrownBy(() -> PetAgeToBirthDateConverter.ageToBirthDate(age))
                .isInstanceOf(BadRequest400.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"5년9개월 ", " 9년3개월"})
        void 성공_문자열앞뒤공백은_제거처리해둬서_OK(final String age) {
            assertDoesNotThrow(() -> PetAgeToBirthDateConverter.ageToBirthDate(age));
        }

    }


    @ParameterizedTest
    @AutoSource
    @Repeat
    void ageToBirthDate(
        @Min(0) @Max(9999) final int year,
        @Min(0) @Max(11) final int month) {
        final String age = year + "년" + month + "개월";

        final LocalDate birthDate = PetAgeToBirthDateConverter.ageToBirthDate(age);

        assertEqualsBirthDate(birthDate, year, month);
    }

    @ParameterizedTest
    @AutoSource
    @Repeat
    void birthDateToAge(@Min(1990) @Max(2023) final int year,
                        @Min(1) @Max(11) final int month) {
        final LocalDate birthDate = LocalDate.of(year, month, 1);

        if(birthDate.isAfter(LocalDate.now())) return;

        final String calculatedAge = PetAgeToBirthDateConverter.birthDateToAge(birthDate);

        final LocalDate now = LocalDate.now();
        final LocalDate expectedBirthDate = now.minusYears(birthDate.getYear()).minusMonths(birthDate.getMonthValue());
        final String expectedAge = expectedBirthDate.getYear() + "년" + expectedBirthDate.getMonthValue() + "개월";

        log.debug("\n birthDate: {},\n expectedAge: {},\n calculatedAge: {}", birthDate, expectedAge, calculatedAge);

        assertThat(calculatedAge).isEqualTo(expectedAge);
    }


    private void assertEqualsBirthDate(final LocalDate birthDate, final int year, final int month) {
        final LocalDate today = LocalDate.now();
        final LocalDate 수동계산된_생일 = today.minusYears(year).minusMonths(month);
        assertAll(
            () -> assertThat(Objects.requireNonNull(birthDate).getYear()).isEqualTo(수동계산된_생일.getYear()),
            () -> assertThat(Objects.requireNonNull(birthDate).getMonthValue()).isEqualTo(수동계산된_생일.getMonthValue())
        );
    }
}