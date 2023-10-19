package com.daggle.animory.domain.pet.service;

import com.daggle.animory.domain.fileserver.S3FileRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PetWriteServiceTransactionManager {
    private final S3FileRepository fileRepository;
    private final PetRepository petRepository;

    /** <pre>
     * 펫 등록 트랜잭션
     * 1. 이미지, 비디오 파일 저장 (URL 반환)
     * 2. 펫 DB 저장
     * </pre> */
    public Pet doPetRegisterTransaction(final PetRegisterRequestDto petRequestDTO,
                                        final MultipartFile image,
                                        final MultipartFile video,
                                        final Shelter shelter) {

        final List<String> savedFileUrls = new ArrayList<>();

        final String imageUrl;
        final String videoUrl;
        final Pet registerPet;

        try{
            // 이미지, 숏폼 파일 저장 후 url을 얻어온다.
            imageUrl = fileRepository.save(image);
            savedFileUrls.add(imageUrl);

            videoUrl = fileRepository.save(video);
            savedFileUrls.add(videoUrl);

            // 펫 DB 저장
            registerPet = petRepository.save(
                petRequestDTO.toEntity(shelter, imageUrl, videoUrl));
        } catch (final RuntimeException e) {
            log.warn("Pet Register 저장 트랜잭션에 문제발생, {}", e.getMessage());
            fileRepository.deleteAll(savedFileUrls); // rollback: 저장된 파일 삭제. 현재는 삭제과정의 예외까지는 고려하지 않는다.

            throw e; // DB는 Transactional이므로 자동 rollback
        }

        return registerPet;
    }
}
