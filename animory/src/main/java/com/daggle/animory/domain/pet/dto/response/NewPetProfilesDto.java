package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.common.RefinedPage;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public class NewPetProfilesDto extends RefinedPage {
    private final List<NewPetDto> newList;

    @Builder
    private NewPetProfilesDto(final Page<?> page, final List<NewPetDto> newList) {
        super(page);
        this.newList = newList;
    }

    public static NewPetProfilesDto of(final Page<?> page, final List<NewPetDto> newList) {
        return NewPetProfilesDto.builder()
            .page(page)
            .newList(newList)
            .build();
    }
}
