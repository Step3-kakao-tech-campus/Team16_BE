package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.common.RefinedPage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SosPetProfilesDto extends RefinedPage {
    private final List<SosPetDto> sosList;

    @Builder
    private SosPetProfilesDto(final Page<?> page, final List<SosPetDto> sosList) {
        super(page);
        this.sosList = sosList;
    }

    public static SosPetProfilesDto of(final Page<?> page, final List<SosPetDto> sosList) {
        return SosPetProfilesDto.builder()
            .page(page)
            .sosList(sosList)
            .build();
    }
}
