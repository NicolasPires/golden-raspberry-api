package outsera.goldenraspberry.application.port.out;

import java.util.List;
import java.util.Map;

public interface MovieQueryPort {
    Map<String, List<Integer>> winnerYearsByProducer();
}
