package com.daggle.animory.service.pet.fileIO;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
}
