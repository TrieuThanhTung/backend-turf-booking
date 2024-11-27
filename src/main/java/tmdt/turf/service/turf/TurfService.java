package tmdt.turf.service.turf;

import tmdt.turf.dto.request.NewTimeSlot;
import tmdt.turf.dto.request.NewTurf;
import tmdt.turf.dto.request.UpdateTurfDto;
import tmdt.turf.dto.response.PageTurfs;
import tmdt.turf.model.turf.Turf;

import java.util.List;

public interface TurfService {
    PageTurfs getEnableTurfs(Integer page);

    void createNewTimeSlot(NewTimeSlot newTimeSlot);

    void createNewTurf(NewTurf newTurf);

    Turf getTurfById(Integer id);

    PageTurfs getTurfsByQuery(String query, Integer page);

    void updateTurfById(Integer id, UpdateTurfDto updateTurfDto);

    PageTurfs getTurfsByOwner(Integer page);
}
