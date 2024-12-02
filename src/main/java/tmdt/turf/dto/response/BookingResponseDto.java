package tmdt.turf.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookingResponseDto {
    private Integer id;
    private Integer turfId;
    private String turfName;
    private String turfAddress;
    private String turfImages;
    private Float location_lat;
    private Float location_lon;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;
    private Double price;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime createdAt;
}
