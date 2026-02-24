package outsera.goldenraspberry.application.port.in;

import outsera.goldenraspberry.domain.model.ProducerIntervalsResult;

public interface GetProducerIntervalsUseCase {
    ProducerIntervalsResult getIntervals();
}
