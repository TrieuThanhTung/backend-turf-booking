package tmdt.turf.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDto {
    private Integer turfId;
    private Integer turfPriceId;
    private LocalDate dateBooking;
}
