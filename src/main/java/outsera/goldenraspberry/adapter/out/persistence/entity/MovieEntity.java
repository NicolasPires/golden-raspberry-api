package outsera.goldenraspberry.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_year", nullable = false)
    private int year;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studios;

    @Column(nullable = false)
    private boolean winner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_producers",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<ProducerEntity> producers = new HashSet<>();

    protected MovieEntity() { }

    public MovieEntity(int year, String title, String studios, boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.winner = winner;
    }

    public void addProducer(ProducerEntity producer) {
        this.producers.add(producer);
    }

    public Long getId() { return id; }
    public int getYear() { return year; }
    public String getTitle() { return title; }
    public String getStudios() { return studios; }
    public boolean isWinner() { return winner; }
    public Set<ProducerEntity> getProducers() { return producers; }
}
