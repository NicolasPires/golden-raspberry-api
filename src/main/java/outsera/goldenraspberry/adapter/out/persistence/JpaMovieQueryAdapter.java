package outsera.goldenraspberry.adapter.out.persistence;

import outsera.goldenraspberry.adapter.out.persistence.repo.MovieJpaRepository;
import outsera.goldenraspberry.application.port.out.MovieQueryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JpaMovieQueryAdapter implements MovieQueryPort {

    private final MovieJpaRepository movieRepository;

    public JpaMovieQueryAdapter(MovieJpaRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Map<String, List<Integer>> winnerYearsByProducer() {
        return movieRepository.findAllWinnersWithProducers().stream()
                .flatMap(movie -> movie.getProducers().stream()
                        .map(p -> Map.entry(p.getName(), movie.getYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }
}
