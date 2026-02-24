package outsera.goldenraspberry.application.service;

import outsera.goldenraspberry.application.port.in.GetProducerIntervalsUseCase;
import outsera.goldenraspberry.application.port.out.MovieQueryPort;
import outsera.goldenraspberry.domain.model.ProducerIntervalsResult;
import outsera.goldenraspberry.domain.service.AwardIntervalCalculator;
import org.springframework.stereotype.Service;

@Service
public class GetProducerIntervalsService implements GetProducerIntervalsUseCase {

    private final MovieQueryPort movieQueryPort;
    private final AwardIntervalCalculator calculator;

    public GetProducerIntervalsService(MovieQueryPort movieQueryPort, AwardIntervalCalculator calculator) {
        this.movieQueryPort = movieQueryPort;
        this.calculator = calculator;
    }

    @Override
    public ProducerIntervalsResult getIntervals() {
        var data = movieQueryPort.winnerYearsByProducer();
        return calculator.calculate(data);
    }
}
