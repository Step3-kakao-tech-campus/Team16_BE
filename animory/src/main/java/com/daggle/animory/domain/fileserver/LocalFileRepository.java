package com.daggle.animory.domain.fileserver;


import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.InternalServerError500;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Repository
public class LocalFileRepository implements FileRepository {
    private final Path fileStorageLocation;

    @Value("${file-server-domain}")
    private String fileUrlPrefix;

    public LocalFileRepository(@Value("${upload-path}") final String path){
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }
        catch(final Exception ex){
            throw new InternalServerError500("디렉토리를 만들 수 없습니다.");
        }
    }

    public Resource getFile(final String fileUrl){
        try{
            return new InputStreamResource(new FileInputStream(fileUrl));
        }catch(FileNotFoundException ex){
            throw new BadRequest400("해당 파일을 찾을 수 없습니다.");
        }
    }

    public String storeFile(final MultipartFile file){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss_");
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String time = simpleDateFormat.format(timestamp);

        final String fileName = time + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try{
            if(fileName.contains("..")){
                throw new BadRequest400("파일이 유효하지 않은 경로를 포함하고 있습니다." + fileName);
            }
            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch(final IOException ex){
            throw new BadRequest400("파일 " + fileName +"을 저장할 수 없습니다. 다시 시도하여주십시오.");
        }
    }
}
