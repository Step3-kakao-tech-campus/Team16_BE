package com.daggle.animory.domain.fileserver;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface FileRepository {

    Resource findByName(String fileName);

    URL save(MultipartFile file);
}
