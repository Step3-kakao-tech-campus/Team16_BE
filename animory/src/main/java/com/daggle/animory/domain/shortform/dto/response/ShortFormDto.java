package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import com.daggle.animory.domain.shelter.entity.Shelter;
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
        final Shelter shelter = pet.getShelter();

        return ShortFormDto.builder()
            .petId(pet.getId())
            .name(pet.getName())
            .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .shelterId(shelter.getId())
            .shelterName(shelter.getName())
            .profileShortFormUrl(petVideo.getVideoUrl())
            .likeCount(LikeCountToStringConverter.convert(petVideo.getLikeCount()))
            .adoptionStatus(pet.getAdoptionStatus().getMessage())
            .build();
    }
}
