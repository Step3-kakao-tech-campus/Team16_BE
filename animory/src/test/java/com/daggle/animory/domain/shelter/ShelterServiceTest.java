package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.error.exception.Forbidden403Exception;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.dto.request.ShelterAddressUpdateDto;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.fixture.PetFixture;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {
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

    @Nested
    class 보호소_수정 {
        @Test
        void 성공_보호소_수정() {
            Account account = Account.builder()
                    .id(1)
                    .build();

            Shelter shelter = Shelter.builder()
                    .id(1)
                    .account(account)
                    .build();

            ShelterUpdateDto shelterUpdateDto = ShelterUpdateDto.builder()
                    .contact("0101010101")
                    .name("변경한 이름")
                    .shelterAddressUpdateDto(ShelterAddressUpdateDto.builder()
                            .province(Province.광주)
                            .city("변경한 시")
                            .roadName("변경한 도로명 주소")
                            .build())
                    .build();

            // stub
            Mockito.when(shelterRepository.findById(any())).thenReturn(Optional.of(shelter));

            ShelterUpdateSuccessDto shelterUpdateSuccessDto = shelterService.updateShelterInfo(account, shelter.getId(), shelterUpdateDto);

            assertAll(
                    () -> assertThat(shelterUpdateSuccessDto.getShelterId()).isEqualTo(shelter.getId()),
                    () -> assertThat(shelter.getName()).isEqualTo(shelterUpdateDto.name()),
                    () -> assertThat(shelter.getAddress().getCity()).isEqualTo(shelterUpdateDto.shelterAddressUpdateDto().city())
            );
        }

        @Test
        void 실패_보호소_수정_권한없음() {
            Account account = Account.builder()
                    .id(1)
                    .build();

            Account otherAccount = Account.builder()
                    .id(2)
                    .build();

            Shelter shelter = Shelter.builder()
                    .id(1)
                    .account(account)
                    .build();

            ShelterUpdateDto shelterUpdateDto = ShelterUpdateDto.builder()
                    .contact("0101010101")
                    .name("변경한 이름")
                    .shelterAddressUpdateDto(ShelterAddressUpdateDto.builder()
                            .province(Province.광주)
                            .city("변경한 시")
                            .roadName("변경한 도로명 주소")
                            .build())
                    .build();

            // stub
            Mockito.when(shelterRepository.findById(any())).thenReturn(Optional.of(shelter));

            assertThatThrownBy(() -> shelterService.updateShelterInfo(otherAccount, shelter.getId(), shelterUpdateDto))
                    .isInstanceOf(Forbidden403Exception.class)
                    .hasMessage("보호소 정보를 수정할 권한이 없습니다.");
        }
    }
}

