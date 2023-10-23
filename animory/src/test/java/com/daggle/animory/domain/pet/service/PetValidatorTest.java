package com.daggle.animory.domain.pet.service;

import com.daggle.animory.domain.shelter.ShelterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = PetValidator.class)
class PetValidatorTest {
    @Autowired
    private PetValidator petValidator;

    @MockBean
    private ShelterRepository shelterRepository;

    @ParameterizedTest
    @ValueSource(strings = {"jpg", "jpeg", "png", "gif", "bmp", "tiff"})
    void validateImageFile(final String extension) {
        final MockMultipartFile image = buildMockFileWithExtension(extension);
        assertDoesNotThrow(() -> petValidator.validateImageFile(image));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mp4", "avi", "mov", "wmv", "flv", "mkv", "webm"})
    void validateVideoFile(final String extension) {
        final MockMultipartFile video = buildMockFileWithExtension(extension);
        assertDoesNotThrow(() -> petValidator.validateVideoFile(video));
    }

    private MockMultipartFile buildMockFileWithExtension(final String extension) {
        return new MockMultipartFile("file", "file." + extension, "application/octet-stream" + extension, "file".getBytes(StandardCharsets.UTF_8));
    }
}