package outsera.goldenraspberry.adapter.out.persistence.repo;

import outsera.goldenraspberry.adapter.out.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {

    @Query("select distinct m from MovieEntity m join fetch m.producers p where m.winner = true")
    List<MovieEntity> findAllWinnersWithProducers();

    long countBy();
}
