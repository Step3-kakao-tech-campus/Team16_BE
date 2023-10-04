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

    private PetProfileSlice(final Page<Pet> page) {
        super(page);
        this.pets = page.getContent().stream().map(PetProfileDto::of).toList();
    }
    public static PetProfileSlice of(Page<Pet> petPage) {
        return new PetProfileSlice(petPage);
    }
}
