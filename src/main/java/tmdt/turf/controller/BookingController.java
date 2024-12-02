package tmdt.turf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tmdt.turf.dto.request.BookingDto;
import tmdt.turf.dto.response.PageBookings;
import tmdt.turf.model.enums.BookingStatus;
import tmdt.turf.service.booking.BookingService;
import tmdt.turf.util.APIResponse;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    final private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody BookingDto bookingDto) {
        bookingService.create(bookingDto);
        return ResponseEntity.status(201).body(new APIResponse("Booking successfully!", null));
    }

    @GetMapping
    public ResponseEntity<?> get (@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                  @RequestParam(name = "status", defaultValue = "PENDING", required = false) BookingStatus status) {
        PageBookings pageBookings = bookingService.get(page, status);
        return ResponseEntity.status(200).body(new APIResponse("Get booking successfully!", pageBookings));
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('TURF_OWNER')")
    public ResponseEntity<?> getWithOwnerRole (@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                  @RequestParam(name = "status", defaultValue = "PENDING", required = false) BookingStatus status) {
        PageBookings pageBookings = bookingService.getWithOwnerRole(page, status);
        return ResponseEntity.status(200).body(new APIResponse("Get booking successfully!", pageBookings));
    }
}
