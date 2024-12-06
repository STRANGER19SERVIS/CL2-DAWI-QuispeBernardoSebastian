package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDetailDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmRegisterDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.dto.FilmUpdateDto;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity.Language;
import pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.service.MaintenanceService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films = maintenanceService.getAllFilms();
        model.addAttribute("films", films);

        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String details(@PathVariable Integer id, Model model) {

        FilmDetailDto filmDetailDto = maintenanceService.getFilmById(id);
        model.addAttribute("filmDetailDto", filmDetailDto);
        return "maintenance-detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {

        FilmUpdateDto filmUpdateDto = maintenanceService.getFilmUpdateById(id);
        model.addAttribute("filmUpdateDto", filmUpdateDto);
        return "maintenance-update";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"), true));
    }

    @PostMapping("/update")
    public String updateFilm(FilmUpdateDto filmUpdateDto) {
        maintenanceService.updateFilm(filmUpdateDto);
        return "redirect:/maintenance/start";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id, Model model) {
        try {
            maintenanceService.deleteFilm(id);
            return "redirect:/maintenance/start";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Pelicula no encontrada");
            return "redirect:/maintenance/start";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        FilmRegisterDto filmRegisterDto = new FilmRegisterDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        model.addAttribute("filmRegisterDto", filmRegisterDto);


        List<Language> languages = maintenanceService.getAllLanguages();
        model.addAttribute("languages", languages);


        return "maintenance-register";
    }


    @PostMapping("/register")
    public String registerFilm(@ModelAttribute FilmRegisterDto filmRegisterDto) {

        maintenanceService.registerNewFilm(filmRegisterDto);
        return "redirect:/maintenance/start";
    }
}
