package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer originalLanguageId;
    private Integer rentalDuration;
    private Double rentalRate;
    private Integer length;
    private Double replacementCost;
    private String rating;
    private String specialFeatures;
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmActor> actors;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmCategory> categories;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventory;
}
