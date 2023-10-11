package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    @GetMapping("/{shelterId}")
    public Response<ShelterProfilePage> getShelter(@PathVariable @Min(0) final Integer shelterId,
                                                   @RequestParam("page") @Min(0) final int page) {
        return Response.success(shelterService.getShelterProfile(shelterId, page));
    }

    /** 등록된 보호소 필터링 API <br>
     * 리스트 형태의 보호소 정보를 입력받아서, DB에서 kakaoLocationId가 일치하는 Shelter목록을 반환한다.
     */
    @PostMapping("/filter")
    public Response<List<ShelterLocationDto>> filterExistShelterListByLocationId(@RequestBody final List<Integer> shelterLocationIdList) {
        return Response.success(shelterService.filterExistShelterListByLocationId(shelterLocationIdList));
    }
}
