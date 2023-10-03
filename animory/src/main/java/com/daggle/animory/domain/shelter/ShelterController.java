package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    @GetMapping("/{shelterId}")
    public Response<ShelterProfilePage> getShelter(@PathVariable Long shelterId, @RequestParam("page") final int page) {
        return Response.success(shelterService.getShelterProfile(shelterId, page));
    }
}
