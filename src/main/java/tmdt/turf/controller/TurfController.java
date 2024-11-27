package tmdt.turf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.request.UpdateTurfDto;
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

    @PostMapping("/time-slot")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createNewTimeSlot(@RequestBody @Valid NewTimeSlot newTimeSlot) {
        turfService.createNewTimeSlot(newTimeSlot);
        return ResponseEntity.ok(new APIResponse("Create success.", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTurfById(@PathVariable Integer id) {
        Turf turfs = turfService.getTurfById(id);
        return ResponseEntity.ok(new APIResponse("Get success.", turfs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTurfById(@PathVariable Integer id, @RequestBody UpdateTurfDto updateTurfDto) {
        turfService.updateTurfById(id, updateTurfDto);
        return ResponseEntity.ok(new APIResponse("Update success.", null));
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('TURF_OWNER')")
    public ResponseEntity<?> getTurfsByOwner(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageTurfs turfs = turfService.getTurfsByOwner(page);
        return ResponseEntity.ok(new APIResponse("Get success.", turfs));
    }

    @GetMapping
    public ResponseEntity<?> getEnableTurfs(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageTurfs turfs = turfService.getEnableTurfs(page);
        return ResponseEntity.ok(new APIResponse("Get success.", turfs));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getTurfsByQuery(@RequestParam(value = "query") String query,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageTurfs turfs = turfService.getTurfsByQuery(query, page);
        return ResponseEntity.ok(new APIResponse("Get success.", turfs));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TURF_OWNER')")
    public ResponseEntity<?> createNewTurf(@RequestBody @Valid NewTurf newTurf) {
        turfService.createNewTurf(newTurf);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse("Create success.", null));
    }
}
