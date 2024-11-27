package tmdt.turf.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tmdt.turf.model.enums.TurfStatus;

import java.util.List;

@Getter
@Setter
public class UpdateTurfDto {
    private String name;
    private String description;
    private String address;
    private Float location_lat;
    private Float location_lon;
    private TurfStatus status;
}
