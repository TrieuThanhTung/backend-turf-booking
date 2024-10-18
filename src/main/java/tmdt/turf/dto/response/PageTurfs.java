package tmdt.turf.dto.response;

import lombok.*;
import tmdt.turf.model.turf.Turf;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PageTurfs {
    private List<Turf> turfs;
    private Integer currentPage;
    private Integer totalPages;
}
