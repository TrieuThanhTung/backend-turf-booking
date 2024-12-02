package tmdt.turf.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tmdt.turf.model.booking.Booking;
import tmdt.turf.model.enums.BookingStatus;
import tmdt.turf.repository.BookingRepository;
import tmdt.turf.service.booking.BookingService;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class ScheduleConfig {
    private final BookingRepository bookingRepository;

    @Scheduled(cron = "*/5 * * * *")
    public void test() {
        List<Booking> bookings = bookingRepository.findByStatus(BookingStatus.PENDING);
        bookings.forEach((booking -> {
            if (booking.getDate().isBefore(LocalDate.now())) {
                booking.setStatus(BookingStatus.DONE);
            }
        }));
        bookingRepository.saveAll(bookings);
        log.info("Run schedule update bookings");
    }
}
