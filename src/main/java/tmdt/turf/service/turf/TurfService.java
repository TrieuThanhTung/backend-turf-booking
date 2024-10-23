package tmdt.turf.service.turf;

import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.model.turf.Turf;

public interface TurfService {
    PageTurfs getEnableTurfs(Integer page);

    void createNewTimeSlot(NewTimeSlot newTimeSlot);

    void createNewTurf(NewTurf newTurf);

    Turf getTurfById(Integer id);
}
