package tmdt.turf.service.booking;

import tmdt.turf.dto.request.BookingDto;
import tmdt.turf.dto.response.PageBookings;
import tmdt.turf.model.enums.BookingStatus;

public interface BookingService {
    void create(BookingDto bookingDto);

    PageBookings get(Integer page, BookingStatus status);
}
