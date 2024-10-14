package tmdt.turf.model.turf;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TurfPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_PRICES_TIME_SLOT"))
    private TimeSlot timeSlot;
    private Double price;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime updatedAt;
}
