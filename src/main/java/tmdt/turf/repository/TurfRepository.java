package tmdt.turf.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdt.turf.model.enums.TurfStatus;
import tmdt.turf.model.turf.Turf;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurfRepository extends JpaRepository<Turf, Integer> {
    Page<Turf> findAllByStatus(TurfStatus status, Pageable pageable);
}
