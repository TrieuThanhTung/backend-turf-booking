package tmdt.turf.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PageBookings {
    private List<BookingResponseDto> bookings;
    private Integer currentPage;
    private Integer totalPages;
}
