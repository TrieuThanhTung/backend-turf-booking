package tmdt.turf.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.request.BookingDto;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.booking.Booking;
import tmdt.turf.model.enums.BookingStatus;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.turf.TurfPrice;
import tmdt.turf.model.user.User;
import tmdt.turf.repository.BookingRepository;
import tmdt.turf.repository.TurfRepository;
import tmdt.turf.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    final private UserRepository userRepository;
    final private TurfRepository turfRepository;
    final private BookingRepository bookingRepository;

    @Override
    public void create(BookingDto bookingDto) {
        User secureUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(secureUser.getId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        Turf turf = turfRepository.findById(bookingDto.getTurfId())
                .orElseThrow(() -> new CustomException("Turf not found", HttpStatus.NOT_FOUND));
        TurfPrice turfPrice = turf.getPrices().stream()
                .filter(price -> price.getId().equals(bookingDto.getTurfPriceId()))
                .findFirst()
                .orElseThrow(() -> new CustomException("Turf price not found", HttpStatus.NOT_FOUND));
        Booking booking = Booking.builder()
                .user(user)
                .turf(turf)
                .startTime(turfPrice.getStart_time())
                .endTime(turfPrice.getEnd_time())
                .date(bookingDto.getDateBooking())
                .price(turfPrice.getPrice())
                .status(BookingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);
    }
}
