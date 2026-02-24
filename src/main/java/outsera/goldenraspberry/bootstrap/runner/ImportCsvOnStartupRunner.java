package outsera.goldenraspberry.bootstrap.runner;

import outsera.goldenraspberry.adapter.out.ingestion.MovieDataIngestionPort;
import outsera.goldenraspberry.application.exception.DataImportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImportCsvOnStartupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ImportCsvOnStartupRunner.class);

    private final MovieDataIngestionPort ingestionPort;

    public ImportCsvOnStartupRunner(MovieDataIngestionPort ingestionPort) {
        this.ingestionPort = ingestionPort;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            ingestionPort.ingest();
            log.info("CSV import completed.");
        } catch (DataImportException ex) {
            log.error("CSV import failed: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
