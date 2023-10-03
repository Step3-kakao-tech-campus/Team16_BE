package com.daggle.animory.domain.fileserver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {

    // 또는 로직이 복잡하다면 중간에 Service 추가
    private final FileRepository localFileRepository;

    @GetMapping("/file/{fileUrl}")
    public ResponseEntity<Resource> getFile(@PathVariable final String fileUrl) {
        return ResponseEntity.ok().body(localFileRepository.getFile(fileUrl));
    }
}
