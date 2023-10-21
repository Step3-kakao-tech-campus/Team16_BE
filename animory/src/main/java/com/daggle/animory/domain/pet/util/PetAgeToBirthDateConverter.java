package com.daggle.animory.domain.pet.util;

import com.daggle.animory.common.error.exception.BadRequest400;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * x년y개월 <br>
 * 0년0개월 ~ 9999년12개월
 *  <br> <br>
 * TL;DR <br>
 * 2개의 메소드만 사용하시면 됩니다. <br>
 * 1. ageToBirthDate(String age) : x년y개월 -> LocalDate <br>
 * 2. birthDateToAge(LocalDate birthDate) : LocalDate -> x년y개월
*/
public class PetAgeToBirthDateConverter {
    private static final Pattern AGE_YEAR = Pattern.compile("년", Pattern.CANON_EQ);
    private static final Pattern AGE_MONTH = Pattern.compile("개월", Pattern.CANON_EQ);
    private static final Pattern AGE_PATTERN = Pattern.compile("\\d+년\\d+개월", Pattern.CANON_EQ);

    private PetAgeToBirthDateConverter() {}

    public static LocalDate ageToBirthDate(final String inputAge) {
        final String age = inputAge.trim();

        validateAgeFormat(age);

        // today - age
        return LocalDate.now()
            .minusYears(getYear(age))
            .minusMonths(getMonth(age));
    }

    public static String birthDateToAge(final LocalDate birthDate) {
        final LocalDate age = LocalDate.now()
            .minusYears(birthDate.getYear())
            .minusMonths(birthDate.getMonthValue());
        return age.getYear() + "년" + age.getMonthValue() + "개월";
    }

    public static void validateAgeFormat(final String age) {
        if( !AGE_PATTERN.matcher(age).matches() ) throw new BadRequest400("잘못된 나이 형식입니다(format): " + age);

        final int year = getYear(age);
        final int month = getMonth(age);

        if( year < 0 || year > 9999 ) throw new BadRequest400("잘못된 나이 형식입니다(year): " + year);
        if( month < 0 || month > 12 ) throw new BadRequest400("잘못된 나이 형식입니다(month): " + month);
    }

    private static int getYear(final String age) {
        return Integer.parseInt(AGE_YEAR.split(age)[0]);
    }

    private static int getMonth(final String age) {
        return Integer.parseInt(AGE_MONTH.split(AGE_YEAR.split(age)[1])[0]);
    }
}
