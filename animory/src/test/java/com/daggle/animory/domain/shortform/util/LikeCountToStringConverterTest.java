package com.daggle.animory.domain.shortform.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class LikeCountToStringConverterTest {


    @ParameterizedTest
    @CsvSource(textBlock = """
        0,0
        1,1
        999,999
        1000,1.0천
        1001,1.0천
        1100,1.1천
        10000,1.0만
        10001,1.0만
        11000,1.1만
        12345,1.2만
        """
    )
    void convert(final int likeCount, final String expected) {
        final String actual = LikeCountToStringConverter.convert(likeCount);

        assertThat(actual).isEqualTo(expected);
    }
}