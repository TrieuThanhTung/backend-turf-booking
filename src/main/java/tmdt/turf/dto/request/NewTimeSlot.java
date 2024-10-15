package tmdt.turf.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class NewTimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
}
