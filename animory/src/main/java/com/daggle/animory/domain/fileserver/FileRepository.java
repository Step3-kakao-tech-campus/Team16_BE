package com.daggle.animory.domain.fileserver;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

    Resource getFile(String fileUrl);

    String storeFile(MultipartFile file);
}
