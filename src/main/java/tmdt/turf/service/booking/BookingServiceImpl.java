package tmdt.turf.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.request.BookingDto;
import tmdt.turf.dto.response.BookingResponseDto;
import tmdt.turf.dto.response.PageBookings;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.booking.Booking;
import tmdt.turf.model.enums.BookingStatus;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.turf.TurfPrice;
import tmdt.turf.model.user.User;
import tmdt.turf.repository.BookingRepository;
import tmdt.turf.repository.TurfRepository;
import tmdt.turf.repository.UserRepository;
import tmdt.turf.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    final private UserRepository userRepository;
    final private TurfRepository turfRepository;
    final private BookingRepository bookingRepository;
    final private UserService userService;

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
        Optional<Booking> optionalBooking = bookingRepository.findByTurfAndDateTime(turf, turfPrice.getStart_time(), bookingDto.getDateBooking());
        if (optionalBooking.isPresent()) throw new CustomException("Turf is booked", HttpStatus.NOT_ACCEPTABLE);
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

    @Override
    public PageBookings get(Integer page, BookingStatus status) {
        User user = userService.getProfile();
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, 5, sortWishItem);
        Page<Booking> bookingPage = bookingRepository.findByStatusAndUser(status, user, pageable);
        List<BookingResponseDto> bookingResponseDtos = bookingPage.getContent().stream()
                .map((booking) -> {
                            BookingResponseDto bookingResponseDto = BookingResponseDto.builder()
                                    .id(booking.getId())
                                    .turfName(booking.getTurf().getName())
                                    .turfAddress(booking.getTurf().getAddress())
                                    .startTime(booking.getStartTime())
                                    .endTime(booking.getEndTime())
                                    .date(booking.getDate())
                                    .price(booking.getPrice())
                                    .createdAt(booking.getCreatedAt())
                                    .build();
                            if (!booking.getTurf().getImages().isEmpty()) {
                                bookingResponseDto.setTurfImages(booking.getTurf().getImages().get(0).getUrl());
                            }
                            return bookingResponseDto;
                        }
                ).toList();
        return PageBookings.builder()
                .bookings(bookingResponseDtos)
                .currentPage(bookingPage.getNumber() + 1)
                .totalPages(bookingPage.getTotalPages())
                .build();
    }
}
