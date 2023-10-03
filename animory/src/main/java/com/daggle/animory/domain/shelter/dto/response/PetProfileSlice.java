package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.common.RefinedPage;
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

    public static PetProfileSlice of(final Page<?> page, final List<PetProfileDto> pets) {
        return PetProfileSlice.builder()
                .page(page)
                .pets(pets)
                .build();
    }
}
