package outsera.goldenraspberry.adapter.in.web.dto;

import java.util.List;

public record ProducerIntervalsResponse(
        List<ProducerIntervalItemResponse> min,
        List<ProducerIntervalItemResponse> max
) {}
