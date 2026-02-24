package outsera.goldenraspberry.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "producers", uniqueConstraints = @UniqueConstraint(name = "uk_producer_name", columnNames = "name"))
public class ProducerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    protected ProducerEntity() { }

    public ProducerEntity(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
