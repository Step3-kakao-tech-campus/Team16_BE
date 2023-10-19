package com.daggle.animory.domain.pet.service;

import com.daggle.animory.common.error.exception.BadRequest400Exception;
import com.daggle.animory.common.error.exception.Forbidden403Exception;
import com.daggle.animory.common.error.exception.NotFound404Exception;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
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
    public void validatePetUpdateAuthority(final Account account,
                                           final Pet pet) {

        // Data Integrity Validation
        final Shelter shelterFromRequest = shelterRepository.findByAccountId(account.getId())
            .orElseThrow(() -> new NotFound404Exception("보호소 정보가 존재하지 않습니다."));
        final Shelter shelterToUpdate = pet.getShelter();

        // Authorization Validation
        if (!shelterFromRequest.equals(shelterToUpdate)) {
            throw new Forbidden403Exception("펫 수정 권한이 없습니다.");
        }
    }

    public void validateImageFile(final MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new BadRequest400Exception("이미지 파일이 존재하지 않습니다.");
        }

        if (!IMAGE_FILE_EXTENSIONS.contains(getFileExtension(image))) {
            throw new BadRequest400Exception("이미지 파일이 아닙니다.");
        }

    }

    public void validateVideoFile(final MultipartFile video) {
        if (video == null || video.isEmpty()) {
            throw new BadRequest400Exception("비디오 파일이 존재하지 않습니다.");
        }

        if (!VIDEO_FILE_EXTENSIONS.contains(getFileExtension(video))) {
            throw new BadRequest400Exception("비디오 파일이 아닙니다.");
        }
    }

    private String getFileExtension(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        return Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".")).substring(1).toLowerCase();
    }
}
