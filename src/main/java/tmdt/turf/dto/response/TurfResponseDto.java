package tmdt.turf.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.turf.TurfImage;
import tmdt.turf.model.turf.TurfPrice;
import tmdt.turf.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TurfResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String address;
    private Float location_lat;
    private Float location_lon;
    private Float rating;
    private TurfStatus status;
    private List<TurfImage> images;
    private List<TurfPrice> prices;
    private User owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
