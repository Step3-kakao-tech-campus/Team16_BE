package com.daggle.animory.domain.fileserver;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public class S3FileRepository implements FileRepository{

    @Override
    public Resource findByName(String fileName) {
        return null;
    }

    @Override
    public URL save(MultipartFile file) {
        return null;
    }
}
