package com.daggle.animory.domain.fileserver;


import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.InternalServerError500;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Slf4j
@Repository
public class LocalFileRepository implements FileRepository {
    private final Path fileStorageLocation;

    private final String fileServerDomain;
    private static final String FILE_REQUEST_ENDPOINT = "/files/"; // Controller 에서 사용하는 endpoint
    private static final String FILE_SAVED_CLASSPATH = "/files/"; // 파일이 저장되어 있는 resource 폴더 내부의 상대경로


    public LocalFileRepository(@Value("${spring.servlet.multipart.location}") final String path,
                               @Value("${file-server-domain}") final String fileServerDomain) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (final Exception ex) {
            throw new InternalServerError500("디렉토리를 만들 수 없습니다.");
        }

        this.fileServerDomain = fileServerDomain;
    }

    public Resource findByName(final String fileName) {
        try {
            final ClassPathResource classPathResource = new ClassPathResource(FILE_SAVED_CLASSPATH + fileName);
            return new InputStreamResource(classPathResource.getInputStream());
        } catch (final FileNotFoundException ex) {
            log.debug("해당 파일을 찾을 수 없습니다.", ex);
            throw new BadRequest400("해당 파일을 찾을 수 없습니다.");
        } catch (final IOException ex) {
            log.debug("파일을 읽을 수 없습니다.", ex);
            throw new InternalServerError500("파일을 읽을 수 없습니다.");
        }
    }

    public URL save(final MultipartFile file) {
        final String fileName = getCurrentTimeString() + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        validateSafeFileName(fileName);

        try {
            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new URL(fileServerDomain + FILE_REQUEST_ENDPOINT + fileName);
        } catch (final IOException ex) {
            throw new BadRequest400("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도하여주십시오.");
        }
    }

    private void validateSafeFileName(final String fileName) {
        if (fileName.contains("..")) {
            throw new BadRequest400("파일이 유효하지 않은 경로를 포함하고 있습니다." + fileName);
        }
    }

    private String getCurrentTimeString() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss_");
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return simpleDateFormat.format(timestamp);
    }
}
