package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.fixture.PetFixture;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest  {
    @InjectMocks
    private ShelterService shelterService;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private PetRepository petRepository;

    @Nested
    class 보호소_조회 {
        @Test
        void 성공_보호소_조회() {
            Shelter shelter = Shelter.builder()
                    .id(1)
                    .build();

            Page<Pet> pets = new PageImpl<>(PetFixture.get(5, PetType.DOG, shelter));

            // stub
            Mockito.when(shelterRepository.findById(any())).thenReturn(Optional.of(shelter));
            Mockito.when(petRepository.findByShelterId(any(), any())).thenReturn(pets);

            ShelterProfilePage shelterProfilePage = shelterService.getShelterProfile(shelter.getId(), 0);

            assertThat(shelterProfilePage.shelter().id()).isEqualTo(shelter.getId());
            assertThat(shelterProfilePage.petList().getSize()).isEqualTo(5);
        }

        @Test
        void 보호소의_아이들이_없을_경우_빈_배열_리턴() {
            Shelter shelter = Shelter.builder()
                    .id(1)
                    .build();

            // stub
            Mockito.when(shelterRepository.findById(any())).thenReturn(Optional.of(shelter));
            Mockito.when(petRepository.findByShelterId(any(), any())).thenReturn(null);

            ShelterProfilePage shelterProfilePage = shelterService.getShelterProfile(shelter.getId(), 0);

            assertThat(shelterProfilePage.shelter().id()).isEqualTo(shelter.getId());
            assertThat(shelterProfilePage.petList().getPets()).isEmpty();
        }
    }
}

