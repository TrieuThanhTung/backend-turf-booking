package tmdt.turf.service.turf;

import org.springframework.data.domain.Page;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.model.turf.Turf;

import java.util.List;

public interface TurfService {
    PageTurfs getEnableTurfs();
}
