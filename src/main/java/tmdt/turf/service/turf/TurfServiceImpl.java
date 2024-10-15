package tmdt.turf.service.turf;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.turf.TimeSlot;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.turf.TurfImage;
import tmdt.turf.model.turf.TurfPrice;
import tmdt.turf.repository.TimeSlotRepository;
import tmdt.turf.repository.TurfRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurfServiceImpl implements TurfService{
    private final TurfRepository turfRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public PageTurfs getEnableTurfs() {
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, 20, sortWishItem);
        Page<Turf> turfPage = turfRepository.findAll(pageable);
        return PageTurfs.builder()
                .turfs(turfPage.getContent())
                .currentPage(turfPage.getNumber() + 1)
                .totalPages(turfPage.getTotalPages())
                .build();
    }

    @Override
    public void createNewTimeSlot(NewTimeSlot newTimeSlot) {
        TimeSlot timeSlot = TimeSlot.builder()
                .startTime(newTimeSlot.getStartTime())
                .endTime(newTimeSlot.getEndTime())
                .build();
        timeSlotRepository.save(timeSlot);
    }

    @Override
    public void createNewTurf(NewTurf newTurfDto) {
        Optional<Turf> turfOptional = turfRepository.findByName(newTurfDto.getName());
        if(turfOptional.isPresent()) throw new CustomException("Turf is available!", HttpStatus.NOT_ACCEPTABLE);
        Turf newTurf = Turf.builder()
                .name(newTurfDto.getName())
                .description(newTurfDto.getDescription())
                .address(newTurfDto.getAddress())
                .location_lat(newTurfDto.getLocation_lat())
                .location_lon(newTurfDto.getLocation_lon())
                .rating(0F)
                .status(TurfStatus.ENABLE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        if(newTurfDto.getStatus() != null) newTurf.setStatus(newTurfDto.getStatus());
        List<TurfImage> turfImages = newTurfDto.getImages().stream()
                .map(urlImage -> TurfImage.builder().url(urlImage).build())
                .toList();
        List<TurfPrice> turfPrices = newTurfDto.getTurfPrices().stream()
                .map(turfPriceDto -> TurfPrice.builder()
                        .start_time(turfPriceDto.getStartTime())
                        .end_time(turfPriceDto.getEndTime())
                        .price(turfPriceDto.getPrice())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
                ).toList();
        newTurf.setImages(turfImages);
        newTurf.setPrices(turfPrices);
        turfRepository.save(newTurf);
    }
}
