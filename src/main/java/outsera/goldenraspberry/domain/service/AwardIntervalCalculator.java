package outsera.goldenraspberry.domain.service;

import outsera.goldenraspberry.domain.model.ProducerInterval;
import outsera.goldenraspberry.domain.model.ProducerIntervalsResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AwardIntervalCalculator {

    public ProducerIntervalsResult calculate(Map<String, List<Integer>> winnerYearsByProducer) {

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        List<ProducerInterval> min = new ArrayList<>();
        List<ProducerInterval> max = new ArrayList<>();

        for (var entry : winnerYearsByProducer.entrySet()) {
            var producer = entry.getKey();
            var sortedYears = entry.getValue().stream().distinct().sorted().toList();

            if (sortedYears.size() < 2) continue;

            for (int i = 0; i < sortedYears.size() - 1; i++) {
                int previous = sortedYears.get(i);
                int following = sortedYears.get(i + 1);
                int interval = following - previous;

                var item = new ProducerInterval(producer, interval, previous, following);

                if (interval < minValue) {
                    minValue = interval;
                    min.clear();
                    min.add(item);
                } else if (interval == minValue) {
                    min.add(item);
                }

                if (interval > maxValue) {
                    maxValue = interval;
                    max.clear();
                    max.add(item);
                } else if (interval == maxValue) {
                    max.add(item);
                }
            }
        }

        if (min.isEmpty() && max.isEmpty()) {
            return new ProducerIntervalsResult(List.of(), List.of());
        }

        var ordering = Comparator.comparing(ProducerInterval::producer)
                .thenComparingInt(ProducerInterval::previousWin);

        return new ProducerIntervalsResult(
                min.stream().sorted(ordering).toList(),
                max.stream().sorted(ordering).toList()
        );
    }
}