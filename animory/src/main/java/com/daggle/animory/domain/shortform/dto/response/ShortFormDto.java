package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import com.daggle.animory.domain.shortform.util.LikeCountToStringConverter;
import lombok.Builder;

@Builder
public record ShortFormDto(
    Integer petId,
    String name,
    String age,
    Integer shelterId,
    String shelterName,
    String profileShortFormUrl,
    String likeCount,
    String adoptionStatus

) {
    public static ShortFormDto of(final PetVideo petVideo) {
        final Pet pet = petVideo.getPet();

        return ShortFormDto.builder()
            .petId(pet.getId())
            .name(pet.getName())
            .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .shelterId(pet.getShelter().getId())
            .shelterName(pet.getShelter().getName())
            .profileShortFormUrl(pet.getPetVideo().getVideoUrl())
            .likeCount(LikeCountToStringConverter.convert(pet.getPetVideo().getLikeCount()))
            .adoptionStatus(pet.getAdoptionStatus().getMessage())
            .build();
    }
}
