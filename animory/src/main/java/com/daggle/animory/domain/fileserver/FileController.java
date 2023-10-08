package com.daggle.animory.domain.fileserver;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.Authorized;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.response.RegisterPetSuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class FileController {

    // 또는 로직이 복잡하다면 중간에 Service 추가
    private final LocalFileRepository localFileRepository;
    private final S3FileRepository s3FileRepository;
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable final String fileName) throws IOException {
        final Path path = Paths.get(fileName);
        final String contentType = Files.probeContentType(path);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("inline")
                        .filename(fileName, StandardCharsets.UTF_8)
                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        final Resource resource = localFileRepository.findByName(fileName);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 테스트 용 임시 URL
    @PostMapping(value = "/file/save", consumes = {"multipart/form-data"})
    public Response<String> saveFileS3(
            @RequestPart(value = "file") final MultipartFile file
    ) {
        return Response.success(s3FileRepository.save(file).getPath());
    }
}
