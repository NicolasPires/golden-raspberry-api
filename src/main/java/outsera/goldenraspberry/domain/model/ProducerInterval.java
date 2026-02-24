package outsera.goldenraspberry.domain.model;

import java.util.Objects;

public record ProducerInterval(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {

    public ProducerInterval {
        Objects.requireNonNull(producer, "producer must not be null");

        if (producer.isBlank()) {
            throw new IllegalArgumentException("producer must not be blank");
        }

        if (interval < 0) {
            throw new IllegalArgumentException("interval must not be negative");
        }

        if (previousWin <= 0 || followingWin <= 0) {
            throw new IllegalArgumentException("years must be positive");
        }

        if (followingWin < previousWin) {
            throw new IllegalArgumentException("followingWin must be greater than previousWin");
        }
    }
}