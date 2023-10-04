package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.common.RefinedPage;
import com.daggle.animory.domain.pet.entity.Pet;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
public class PetProfileSlice extends RefinedPage {
    private final List<PetProfileDto> pets;

    @Builder
    private PetProfileSlice(final Page<?> page, final List<PetProfileDto> pets) {
        super(page);
        this.pets = pets;
    }
    public static PetProfileSlice of(Page<Pet> petPage) {
        return PetProfileSlice.builder()
                .page(petPage)
                .pets(petPage.getContent().stream().map(PetProfileDto::of).toList())
                .build();
    }
}
