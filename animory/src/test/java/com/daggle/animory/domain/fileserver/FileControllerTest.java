package com.daggle.animory.domain.fileserver;

import com.daggle.animory.testutil.webmvctest.BaseWebMvcTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(FileController.class)
class FileControllerTest extends BaseWebMvcTest {
    @MockBean
    LocalFileRepository localFileRepository;

    @Nested
    class 파일불러오기 {
        @Test
        void 성공_파일_불러오기() throws Exception {
            final ResultActions resultActions = mvc.perform(
                    get("/file/somefile.mp4"))
                .andExpect(status().isOk())
                .andDo(print());
        }
    }
}