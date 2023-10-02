package com.daggle.animory.domain.pet.service.fileIO;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
}
