package com.daggle.animory.service.pet.fileIO;

import com.daggle.animory.common.errors.exception.BadRequest400;
import com.daggle.animory.common.errors.exception.InternalServerError500;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class PetFileStorageServiceWithoutSaving implements FileStorageService{
    private final Path fileStorageLocation;

    public PetFileStorageServiceWithoutSaving (@Value("${upload-path}") String path){
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }
        catch(Exception ex){
            throw new InternalServerError500("디렉토리를 만들 수 없습니다.");
        }
    }

    public String storeFile(MultipartFile file){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss_");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = simpleDateFormat.format(timestamp);

        String fileName = time + StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new BadRequest400("파일이 유효하지 않은 경로를 포함하고 있습니다." + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            if(file == null){
                throw new IOException();
            }
            return fileName;
        }catch(IOException ex){
            throw new BadRequest400("파일 " + fileName +"을 저장할 수 없습니다. 다시 시도하여주십시오.");
        }
    }
}
