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
public class NewTurf {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String address;
    @NotNull
    private Float location_lat;
    @NotNull
    private Float location_lon;
    private TurfStatus status;
    @NotNull
    private List<String> images;
    @NotNull
    @Valid
    private List<NewTurfPrice> turfPrices;
}
