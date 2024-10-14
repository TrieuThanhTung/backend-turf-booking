package tmdt.turf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.service.turf.TurfService;
import tmdt.turf.util.APIResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/turfs")
public class TurfController {
    private final TurfService turfService;

    @GetMapping
    public ResponseEntity<?> getEnableTurfs() {
        PageTurfs turfs = turfService.getEnableTurfs();
        return ResponseEntity.ok(new APIResponse("Get success.", turfs));
    }


}
