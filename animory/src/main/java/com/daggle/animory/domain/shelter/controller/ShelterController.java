package com.daggle.animory.domain.shelter.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.shelter.ShelterService;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/shelter")
public class ShelterController implements ShelterControllerApi {
    private final ShelterService shelterService;

    @Override
    @GetMapping("/{shelterId}")
    public Response<ShelterProfilePage> getShelter(@PathVariable @Min(0) final Integer shelterId,
                                                   @PageableDefault(page = 1, size = 10) final Pageable pageable) {
        return Response.success(shelterService.getShelterProfile(shelterId, pageable));
    }

    @PutMapping("/{shelterId}")
    public Response<ShelterUpdateSuccessDto> updateShelter(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                                           @PathVariable @Min(0) final Integer shelterId,
                                                           @RequestBody final ShelterUpdateDto shelterUpdateDto) {
        return Response.success(shelterService.updateShelterInfo(userDetails, shelterId, shelterUpdateDto));
    }

    /**
     * 등록된 보호소 필터링 API <br>
     * 리스트 형태의 보호소 정보를 입력받아서, DB에서 kakaoLocationId가 일치하는 Shelter목록을 반환한다.
     */
    @PostMapping("/filter")
    public Response<List<ShelterLocationDto>> filterExistShelterListByLocationId(@RequestBody final List<Integer> shelterLocationIdList) {
        return Response.success(shelterService.filterExistShelterListByLocationId(shelterLocationIdList));
    }
}
