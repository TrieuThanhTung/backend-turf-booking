package tmdt.turf.model.turf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.user.User;

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
    @Column(columnDefinition = "TEXT")
    private String description;
    private String address;
    @Column(columnDefinition = "DECIMAL(9, 6)")
    private Float location_lat;
    @Column(columnDefinition = "DECIMAL(9, 6)")
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "owner_id",
            foreignKey = @ForeignKey(name = "FK_TURF_USERS")
    )
    private User owner;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime updatedAt;
}
