package com.daggle.animory.domain.pet.service;

import autoparams.AutoSource;
import autoparams.Repeat;
import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
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
        @Min(0) @Max(12) final int month) {
        final String age = year + "년" + month + "개월";

        final LocalDate birthDate = PetAgeToBirthDateConverter.ageToBirthDate(age);

        assertEqualsBirthDate(birthDate, year, month);
    }

    @ParameterizedTest
    @AutoSource
    @Repeat
    void birthDateToAge(final LocalDate birthDate) {
        final String age = PetAgeToBirthDateConverter.birthDateToAge(birthDate);

        assertThat(age).isEqualTo(birthDate.getYear() + "년" + birthDate.getMonthValue() + "개월");
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