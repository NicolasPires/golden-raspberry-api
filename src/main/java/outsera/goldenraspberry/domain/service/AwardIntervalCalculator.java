package outsera.goldenraspberry.domain.service;

import outsera.goldenraspberry.domain.model.ProducerInterval;
import outsera.goldenraspberry.domain.model.ProducerIntervalsResult;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AwardIntervalCalculator {

    public ProducerIntervalsResult calculate(Map<String, List<Integer>> winnerYearsByProducer) {

        var allIntervals = winnerYearsByProducer.entrySet().stream()
                .flatMap(entry -> buildIntervals(entry.getKey(), entry.getValue()).stream())
                .toList();

        if (allIntervals.isEmpty()) {
            return new ProducerIntervalsResult(List.of(), List.of());
        }

        var minValue = allIntervals.stream()
                .mapToInt(ProducerInterval::interval)
                .min()
                .orElse(0);

        var maxValue = allIntervals.stream()
                .mapToInt(ProducerInterval::interval)
                .max()
                .orElse(0);

        var min = allIntervals.stream()
                .filter(i -> i.interval() == minValue)
                .sorted(Comparator.comparing(ProducerInterval::producer).thenComparingInt(ProducerInterval::previousWin))
                .toList();

        var max = allIntervals.stream()
                .filter(i -> i.interval() == maxValue)
                .sorted(Comparator.comparing(ProducerInterval::producer).thenComparingInt(ProducerInterval::previousWin))
                .toList();

        return new ProducerIntervalsResult(min, max);
    }

    private List<ProducerInterval> buildIntervals(String producer, List<Integer> years) {
        var sortedYears = years.stream().distinct().sorted().toList();
        if (sortedYears.size() < 2) return List.of();

        return IntStream.range(0, sortedYears.size() - 1)
                .mapToObj(i -> {
                    var previous = sortedYears.get(i);
                    var following = sortedYears.get(i + 1);
                    return new ProducerInterval(producer, following - previous, previous, following);
                })
                .toList();
    }
}
