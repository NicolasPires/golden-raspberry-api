package outsera.goldenraspberry.adapter.out.persistence.repo;

import outsera.goldenraspberry.adapter.out.persistence.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerJpaRepository extends JpaRepository<ProducerEntity, Long> {
    Optional<ProducerEntity> findByNameIgnoreCase(String name);
}
