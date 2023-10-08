package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.common.RefinedPage;
import com.daggle.animory.domain.pet.entity.Pet;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class NewPetProfilesDto extends RefinedPage {

    private final List<NewPetDto> newList;

    @Builder
    private NewPetProfilesDto(final Page<Pet> page) {
        super(page);
        this.newList = page.getContent().stream().map(NewPetDto::fromEntity).toList();
    }

    private NewPetProfilesDto() {
        this.pageNumber = 0;
        this.size = 0;
        this.totalPages = 0;
        this.newList = new ArrayList<>();
    }

    public static NewPetProfilesDto of(final Page<Pet> page) {
        if (page == null) {
            return new NewPetProfilesDto();
        }

        return new NewPetProfilesDto(page);
    }
}
