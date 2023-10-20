package com.daggle.animory.domain.fileserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class S3UtilTest {

    @Test
    void getFileNameFromUrl() {
        final String url = "https://kakao-techcampus.s3.ap-northeast-2.amazonaws.com/clHUJdvayRx-2Iuh.mp4";

        final String fileName = S3Util.getFileNameFromUrl(url);

        assertEquals("clHUJdvayRx-2Iuh.mp4", fileName);
    }
}