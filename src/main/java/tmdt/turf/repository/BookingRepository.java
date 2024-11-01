package tmdt.turf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tmdt.turf.model.booking.Booking;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking b " +
            "where b.turf = ?1 and b.startTime = ?2 and b.date = ?3")
    Optional<Booking> findByTurfAndDateTime(Turf turf, LocalTime startTime, LocalDate date);
}
