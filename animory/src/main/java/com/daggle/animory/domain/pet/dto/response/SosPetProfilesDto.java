package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.common.RefinedPage;
import com.daggle.animory.domain.pet.entity.Pet;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class SosPetProfilesDto extends RefinedPage {

    private final List<SosPetDto> sosList;

    @Builder
    private SosPetProfilesDto(final Page<Pet> page) {
        super(page);
        this.sosList = page.getContent().stream().map(SosPetDto::fromEntity).toList();
    }

    private SosPetProfilesDto() {
        this.pageNumber = 0;
        this.size = 0;
        this.totalPages = 0;
        this.sosList = new ArrayList<>();
    }

    public static SosPetProfilesDto of(final Page<Pet> page) {
        if (page == null) {
            return new SosPetProfilesDto();
        }
        return new SosPetProfilesDto(page);
    }
}
