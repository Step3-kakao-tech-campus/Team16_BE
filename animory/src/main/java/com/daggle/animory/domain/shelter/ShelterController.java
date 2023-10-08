package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
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
}
