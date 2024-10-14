package tmdt.turf.service.turf;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.turf.Turf;
import tmdt.turf.repository.TurfRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurfServiceImpl implements TurfService{
    private final TurfRepository turfRepository;

    @Override
    public PageTurfs getEnableTurfs() {
        Sort sortWishItem = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(1, 20, sortWishItem);
        Page<Turf> turfPage = turfRepository.findAllByStatus(TurfStatus.ENABLE, pageable);
        return PageTurfs.builder()
                .turfs(turfPage.getContent())
                .currentPage(turfPage.getNumber() + 1)
                .totalPages(turfPage.getTotalPages())
                .build();
    }
}
