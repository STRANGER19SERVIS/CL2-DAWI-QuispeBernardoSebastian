package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.service;

import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDetailDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmRegisterDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmUpdateDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity.Language;

import java.util.List;

public interface MaintenanceService {
    List<FilmDto> getAllFilms();

    FilmDetailDto getFilmById(int id);

    FilmUpdateDto getFilmUpdateById(int id);

    void updateFilm(FilmUpdateDto filmUpdateDto);

    void deleteFilm(int id);

    void registerNewFilm(FilmRegisterDto filmRegisterDto);

    List<Language> getAllLanguages();
}
