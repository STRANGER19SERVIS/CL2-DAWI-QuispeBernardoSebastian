package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDetailDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmRegisterDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmUpdateDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity.Film;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity.Language;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.repository.FilmRepository;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.repository.LanguageRepository;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.service.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<FilmDto> getAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),film.getTitle(),film.getLanguage().getName(),film.getRentalRate());
            films.add(filmDto);
        });

        return films;
    }

    @Override
    public FilmDetailDto getFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetailDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getLanguage().getLanguageId(),
                        film.getLanguage().getName(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);
    }

    @Override
    public FilmUpdateDto getFilmUpdateById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmUpdateDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);
    }

    @Override
    public void updateFilm(FilmUpdateDto filmUpdateDto) {
        Optional<Film> optionalFilm = filmRepository.findById(filmUpdateDto.filmId());

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();

            film.setTitle(filmUpdateDto.title());
            film.setDescription(filmUpdateDto.description());
            film.setReleaseYear(filmUpdateDto.releaseYear());
            film.setRentalDuration(filmUpdateDto.rentalDuration());
            film.setRentalRate(filmUpdateDto.rentalRate());
            film.setLength(filmUpdateDto.length());
            film.setReplacementCost(filmUpdateDto.replacementCost());
            film.setRating(filmUpdateDto.rating());
            film.setSpecialFeatures(filmUpdateDto.specialFeatures());
            film.setLastUpdate(filmUpdateDto.lastUpdate());


            filmRepository.save(film);
        }
    }

    @Override
    @Transactional
    public void deleteFilm(int filmId) {
        Optional<Film> optionalFilm = filmRepository.findById(filmId);

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();


            String deleteRentalsQuery = "DELETE FROM rental WHERE inventory_id IN (SELECT inventory_id FROM inventory WHERE film_id = ?)";
            jdbcTemplate.update(deleteRentalsQuery, filmId);


            filmRepository.delete(film);
        } else {
            throw new RuntimeException("Pelicula no encontrada" + filmId);
        }
    }

    @Override
    public void registerNewFilm(FilmRegisterDto filmRegisterDto) {

        Language language = languageRepository.findById(filmRegisterDto.languageId()).orElseThrow(() -> new RuntimeException("Lenguaje no encontrado"));

        Film film = new Film();
        film.setTitle(filmRegisterDto.title());
        film.setDescription(filmRegisterDto.description());
        film.setReleaseYear(filmRegisterDto.releaseYear());
        film.setLanguage(language);
        film.setRentalDuration(filmRegisterDto.rentalDuration());
        film.setRentalRate(filmRegisterDto.rentalRate());
        film.setLength(filmRegisterDto.length());
        film.setReplacementCost(filmRegisterDto.replacementCost());
        film.setRating(filmRegisterDto.rating());
        film.setSpecialFeatures(filmRegisterDto.specialFeatures());
        film.setLastUpdate(filmRegisterDto.lastUpdate());

        filmRepository.save(film);

    }

    public List<Language> getAllLanguages() {
        Iterable<Language> iterable = languageRepository.findAll();
        List<Language> languages = new ArrayList<>();
        iterable.forEach(languages::add);
        return languages;
    }
}
