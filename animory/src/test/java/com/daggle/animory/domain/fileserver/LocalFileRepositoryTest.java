package com.daggle.animory.domain.fileserver;

import com.daggle.animory.testutil.datajpatest.WithTimeSupportObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;

// TODO: test를 수행하면 파일이 남아있게 되므로, 코드를 삭제하지 않고 남겨두는 대신 자동으로 실행되지는 않게 하는 방법이..?
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class LocalFileRepositoryTest extends WithTimeSupportObjectMapper {

//    @Autowired
//    LocalFileRepository localFileRepository;
//
//    @Test
//    void getFile() {
//        final Resource file = localFileRepository.findByName("20231003101041_test.txt");
//
//        print(file);
//
//        assertNotNull(file);
//    }
//
//    @Test
//    void save() {
//        final MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
//
//        final URL url = localFileRepository.save(file);
//
//        print(url);
//
//        assertNotNull(url);// delete file
//    }
}