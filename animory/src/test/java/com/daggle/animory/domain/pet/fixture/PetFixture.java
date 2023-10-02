package com.daggle.animory.domain.pet.fixture;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.Province;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PetFixture {

    public static Pet getOne() {
        return Pet.builder()
            .name("멍멍이")
            .birthDate(LocalDate.now().minusMonths(6))
            .type(PetType.DOG)
            .weight(5.0f)
            .description("멍멍이는 귀여워요")
            .protectionExpirationDate(LocalDate.now().plusMonths(6))
            .vaccinationStatus("접종완료")
            .neutralizationStatus(NeutralizationStatus.UNKNOWN)
            .adoptionStatus(AdoptionStatus.NO)
            .profileImageUrl("http://amazon.server/api/petImage/20231001104521_test1.jpg")
            .profileShortFormUrl("http://amazon.server/api/petVideo/20231001104521_test1.mp4")
            .size("작아요 소형견입니다.")
            .shelter(null) // TODO: shelter fixture 구현되지 않음(우선 순서로 생성되어야 함)
            .build();
    }

    public static List<Pet> get(final int n,
                                final PetType petType,
                                final Province area) {
        final List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pets.add(
                Pet.builder()
                    .name("멍멍이" + i)
                    .birthDate(LocalDate.now().minusMonths(6))
                    .type(PetType.DOG)
                    .weight(5.0f)
                    .description("멍멍이는 귀여워요" + i)
                    .protectionExpirationDate(LocalDate.now().plusMonths(1))
                    .vaccinationStatus("접종완료" + i)
                    .neutralizationStatus(NeutralizationStatus.UNKNOWN)
                    .adoptionStatus(AdoptionStatus.NO)
                    .profileImageUrl("http://amazon.server/api/petImage/20231001104521_test" + i + ".jpg")
                    .profileShortFormUrl("http://amazon.server/api/petVideo/20231001104521_test" + i + ".mp4")
                    .size("작아요 소형견입니다." + i)
                    .shelter(null) // TODO: shelter fixture 구현되지 않음(우선 순서로 생성되어야 함)
                    .build()
            );
        }
        return pets;
    }
}
