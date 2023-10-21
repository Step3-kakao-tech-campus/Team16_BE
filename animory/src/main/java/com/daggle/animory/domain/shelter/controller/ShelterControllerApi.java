package com.daggle.animory.domain.shelter.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "보호소 API", description = "보호소 관련 API 입니다.")
public interface ShelterControllerApi {

    @Operation(summary = "보호소 프로필 조회", description = "보호소 프로필을 조회합니다.")
    @GetMapping("/{shelterId}")
    Response<ShelterProfilePage> getShelter(@PathVariable @Min(0) Integer shelterId,
                                            @RequestParam("page") @Min(0) int page);

    @Operation(summary = "[로그인 필요: 보호소] 보호소 정보 수정", description = "보호소 정보를 수정합니다.")
    @PutMapping("/{shelterId}")
    Response<ShelterUpdateSuccessDto> updateShelter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable @Min(0) Integer shelterId,
                                                    @RequestBody ShelterUpdateDto shelterUpdateDto);

    @Operation(summary = "등록된 보호소 필터링", description = "보호소 ID 배열을 입력받아서, DB에서 kakaoLocationId가 일치하는 보호소 정보를 목록으로 반환합니다.")
    @PostMapping("/filter")
    Response<List<ShelterLocationDto>> filterExistShelterListByLocationId(@RequestBody List<Integer> shelterLocationIdList);
}
