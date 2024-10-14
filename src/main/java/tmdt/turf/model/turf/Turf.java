package tmdt.turf.model.turf;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import tmdt.turf.model.enums.TurfStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Turf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String address;
    private Float location_lat;
    private Float location_lon;
    private Float rating;
    @Enumerated(EnumType.STRING)
    private TurfStatus status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "turf_id",
            foreignKey = @ForeignKey(name = "FK_TURF_IMAGES")
    )
    private List<TurfImage> images;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "turf_id",
            foreignKey = @ForeignKey(name = "FK_TURF_PRICES")
    )
    private List<TurfPrice> prices;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime updatedAt;
}
