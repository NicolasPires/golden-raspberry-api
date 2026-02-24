package outsera.goldenraspberry.domain.model;

import java.util.Objects;

public record ProducerWin(
        String producer,
        int year
) {

    public ProducerWin {
        Objects.requireNonNull(producer, "producer must not be null");

        if (producer.isBlank()) {
            throw new IllegalArgumentException("producer must not be blank");
        }

        if (year <= 0) {
            throw new IllegalArgumentException("year must be a positive number");
        }
    }
}