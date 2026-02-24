package outsera.goldenraspberry.domain.model;

import java.util.List;
import java.util.Objects;

public record ProducerIntervalsResult(
        List<ProducerInterval> min,
        List<ProducerInterval> max
) {

    public ProducerIntervalsResult {
        Objects.requireNonNull(min, "min list must not be null");
        Objects.requireNonNull(max, "max list must not be null");

        if (min.isEmpty() && max.isEmpty()) {
            throw new IllegalArgumentException("At least one interval result must be present");
        }
    }
}