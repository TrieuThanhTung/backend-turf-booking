package tmdt.turf.service.turf;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.request.UpdateTurfDto;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.turf.TimeSlot;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.model.turf.TurfImage;
import tmdt.turf.model.turf.TurfPrice;
import tmdt.turf.model.user.User;
import tmdt.turf.repository.TimeSlotRepository;
import tmdt.turf.repository.TurfRepository;
import tmdt.turf.repository.UserRepository;
import tmdt.turf.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurfServiceImpl implements TurfService{
    private final TurfRepository turfRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public PageTurfs getEnableTurfs(Integer page) {
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, 20, sortWishItem);
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
        User user = userService.getProfile();
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
        newTurf.setOwner(user);
        turfRepository.save(newTurf);
    }

    @Override
    public Turf getTurfById(Integer id) {
        return turfRepository.findById(id)
                .orElseThrow(() -> new CustomException("Turf not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public PageTurfs getTurfsByQuery(String query, Integer page) {
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, 20, sortWishItem);
        Page<Turf> turfPage = turfRepository.findByNameContainingOrAddressContaining(query, query, pageable);
        return PageTurfs.builder()
                .turfs(turfPage.getContent())
                .currentPage(turfPage.getNumber() + 1)
                .totalPages(turfPage.getTotalPages())
                .build();
    }

    @Override
    public void updateTurfById(Integer id, UpdateTurfDto updateTurfDto) {
        Turf turf = getTurfById(id);
        User user = userService.getProfile();
        if(!turf.getOwner().getId().equals(user.getId())) throw new CustomException("Turf not found", HttpStatus.NOT_FOUND);
        if(Objects.nonNull(updateTurfDto.getName()) && !turf.getName().equals(updateTurfDto.getName())) {
            turf.setName(updateTurfDto.getName());
        }
        if(Objects.nonNull(updateTurfDto.getDescription()) && !turf.getDescription().equals(updateTurfDto.getDescription())) {
            turf.setDescription(updateTurfDto.getDescription());
        }
        if(Objects.nonNull(updateTurfDto.getAddress()) && !turf.getAddress().equals(updateTurfDto.getAddress())) {
            turf.setAddress(updateTurfDto.getAddress());
        }
        if(Objects.nonNull(updateTurfDto.getLocation_lat()) && !turf.getLocation_lat().equals(updateTurfDto.getLocation_lat())) {
            turf.setLocation_lat(updateTurfDto.getLocation_lat());
        }
        if(Objects.nonNull(updateTurfDto.getLocation_lon()) && !turf.getLocation_lon().equals(updateTurfDto.getLocation_lon())) {
            turf.setLocation_lon(updateTurfDto.getLocation_lon());
        }
        turfRepository.save(turf);
    }

    @Override
    public PageTurfs getTurfsByOwner(Integer page) {
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, 20, sortWishItem);
        User secureUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Turf> turfPage = turfRepository.findByOwnerId(secureUser.getId(), pageable);
        return PageTurfs.builder()
                .turfs(turfPage.getContent())
                .currentPage(turfPage.getNumber() + 1)
                .totalPages(turfPage.getTotalPages())
                .build();
    }
}
