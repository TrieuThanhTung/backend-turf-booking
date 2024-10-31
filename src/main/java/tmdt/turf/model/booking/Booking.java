package tmdt.turf.model.booking;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import tmdt.turf.model.enums.BookingStatus;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "FK_BOOKING_USERS")
    )
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "FK_BOOKING_TURFS")
    )
    private Turf turf;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalTime endTime;
    private LocalDate date;
    private Double price;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm MM/dd/yyyy")
    private LocalDateTime updatedAt;
}
