package tmdt.turf.service.turf;

import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.response.PageTurfs;

public interface TurfService {
    PageTurfs getEnableTurfs();

    void createNewTimeSlot(NewTimeSlot newTimeSlot);

    void createNewTurf(NewTurf newTurf);
}
