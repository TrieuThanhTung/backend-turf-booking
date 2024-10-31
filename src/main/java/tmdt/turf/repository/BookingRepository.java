package tmdt.turf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdt.turf.model.booking.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
