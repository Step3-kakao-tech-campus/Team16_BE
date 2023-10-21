package com.daggle.animory.domain.pet.service;

import com.daggle.animory.domain.fileserver.exeption.InvalidImageTypeException;
import com.daggle.animory.domain.fileserver.exeption.InvalidVideoTypeException;
import com.daggle.animory.domain.fileserver.exeption.NotFoundImageException;
import com.daggle.animory.domain.fileserver.exeption.NotFoundVideoException;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.exception.PetPermissionDeniedException;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.exception.ShelterNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetValidator {

    private final ShelterRepository shelterRepository;

    private static final List<String> IMAGE_FILE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "bmp", "tiff");
    private static final List<String> VIDEO_FILE_EXTENSIONS = List.of("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm");

    /**
     * <pre>
     * 펫 수정 요청이 올바른지 검사합니다.
     *
     * 1. 데이터 무결성 검사
     * 요청한 계정의 보호소가 존재하는지 확인합니다.
     *
     * 2. 수정 권한 검사
     * 수정과 관련된 요청에서, 펫 수정 권한이 있는지 확인합니다.*
     * 요청받은 계정에 연결된 보호소가 같은지 확인합니다.
     * </pre>
     */
    public void validatePetUpdateAuthority(final String email,
                                           final Pet pet) {

        // Data Integrity Validation
        final Shelter shelterFromRequest = shelterRepository.findByAccountEmail(email)
            .orElseThrow(() -> new ShelterNotFoundException());
        final Shelter shelterToUpdate = pet.getShelter();

        // Authorization Validation
        if (!shelterFromRequest.equals(shelterToUpdate)) {
            throw new PetPermissionDeniedException();
        }
    }

    public void validateImageFile(final MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new NotFoundImageException();
        }

        if (!IMAGE_FILE_EXTENSIONS.contains(getFileExtension(image))) {
            throw new InvalidImageTypeException();
        }
    }

    public void validateVideoFile(final MultipartFile video) {
        if (video == null || video.isEmpty()) {
            throw new NotFoundVideoException();
        }

        if (!VIDEO_FILE_EXTENSIONS.contains(getFileExtension(video))) {
            throw new InvalidVideoTypeException();
        }
    }

    private String getFileExtension(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        return Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".")).substring(1).toLowerCase();
    }
}
