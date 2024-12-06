package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto;

import java.util.Date;

public record FilmUpdateDto(Integer filmId,
                            String title,
                            String description,
                            Integer releaseYear,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures,
                            Date lastUpdate) {
}
