package outsera.goldenraspberry.adapter.out.ingestion;

import outsera.goldenraspberry.adapter.out.persistence.entity.MovieEntity;
import outsera.goldenraspberry.adapter.out.persistence.entity.ProducerEntity;
import outsera.goldenraspberry.adapter.out.persistence.repo.MovieJpaRepository;
import outsera.goldenraspberry.adapter.out.persistence.repo.ProducerJpaRepository;
import outsera.goldenraspberry.application.exception.DataImportException;
import outsera.goldenraspberry.application.exception.InvalidCsvException;
import outsera.goldenraspberry.bootstrap.config.IngestionProperties;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvMovieDataIngestionAdapter implements MovieDataIngestionPort {

    private static final List<String> HEADERS = List.of("year", "title", "studios", "producers", "winner");

    private final IngestionProperties properties;
    private final ResourceLoader resourceLoader;
    private final MovieJpaRepository movieRepository;
    private final ProducerJpaRepository producerRepository;

    public CsvMovieDataIngestionAdapter(
            IngestionProperties properties,
            ResourceLoader resourceLoader,
            MovieJpaRepository movieRepository,
            ProducerJpaRepository producerRepository
    ) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
    }

    @Override
    @Transactional
    public void ingest() {
        if (movieRepository.countBy() > 0) {
            return;
        }

        var resource = resourceLoader.getResource(properties.csv());
        if (!resource.exists()) {
            throw new DataImportException("CSV resource not found: " + properties.csv());
        }

        try (var reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             var parser = CSVParser.parse(reader, CSVFormat.DEFAULT
                     .builder()
                     .setDelimiter(';')
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            validateHeaders(parser);

            for (CSVRecord record : parser) {
                persistRecord(record);
            }

        } catch (InvalidCsvException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DataImportException("Failed to import CSV", ex);
        }
    }

    private void validateHeaders(CSVParser parser) {
        var csvHeaders = parser.getHeaderMap().keySet().stream().map(String::toLowerCase).toList();
        if (!csvHeaders.containsAll(HEADERS)) {
            throw new InvalidCsvException("CSV headers invalid. Expected: " + HEADERS + ", got: " + csvHeaders);
        }
    }

    private void persistRecord(CSVRecord record) {
        var year = parseInt(record.get("year"), "year");
        var title = required(record.get("title"), "title");
        var studios = required(record.get("studios"), "studios");
        var producersRaw = required(record.get("producers"), "producers");
        var winner = "yes".equalsIgnoreCase(record.get("winner").trim());

        var movie = new MovieEntity(year, title, studios, winner);

        splitProducers(producersRaw).stream()
                .map(this::findOrCreateProducer)
                .forEach(movie::addProducer);

        movieRepository.save(movie);
    }

    private ProducerEntity findOrCreateProducer(String name) {
        return producerRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> producerRepository.save(new ProducerEntity(name)));
    }

    private List<String> splitProducers(String raw) {
        var normalized = raw.replace(" and ", ",");
        return Arrays.stream(normalized.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private String required(String value, String field) {
        var v = value == null ? "" : value.trim();
        if (v.isBlank()) throw new InvalidCsvException("CSV field '%s' is required".formatted(field));
        return v;
    }

    private int parseInt(String value, String field) {
        try {
            return Integer.parseInt(required(value, field));
        } catch (NumberFormatException ex) {
            throw new InvalidCsvException("CSV field '%s' must be an integer".formatted(field), ex);
        }
    }
}
