package com.daggle.animory.domain.fileserver;


import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class S3Util {
    private S3Util() {
    }

    public static ObjectMetadata buildObjectMetadata(final MultipartFile file,
                                                     final String fileName) throws IOException {
        final ObjectMetadata metadata = new ObjectMetadata();

        // get content type
        final Path path = Paths.get(Objects.requireNonNull(fileName));
        final String contentType = Files.probeContentType(path); // IOException here

        // set metadata
        metadata.setContentType(contentType);
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        return metadata;
    }

    public static String getFileNameFromUrl(final String url) {
        final String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }
}
