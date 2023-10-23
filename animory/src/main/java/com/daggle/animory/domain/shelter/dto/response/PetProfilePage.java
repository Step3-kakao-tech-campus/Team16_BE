package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.common.RefinedPage;
import com.daggle.animory.domain.pet.entity.Pet;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
@Getter
public class PetProfilePage extends RefinedPage {
    private final List<PetProfileDto> pets;

    private PetProfilePage(final Page<Pet> page) {
        super(page);
        this.pets = page.getContent().stream().map(PetProfileDto::of).toList();
    }
    private PetProfilePage() {
        this.pageNumber = 0;
        this.size = 0;
        this.totalPages = 0;
        this.pets = new ArrayList<>();
    }

    public static PetProfilePage of(final Page<Pet> petPage) {
        if (petPage == null) return new PetProfilePage();
        return new PetProfilePage(petPage);
    }
}
