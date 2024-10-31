package tmdt.turf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmdt.turf.dto.request.BookingDto;
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
}
