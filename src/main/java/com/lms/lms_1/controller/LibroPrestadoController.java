package com.lms.lms_1.controller;

import com.lms.lms_1.domain.Lector;
import com.lms.lms_1.domain.Libro;
import com.lms.lms_1.model.LibroPrestadoDTO;
import com.lms.lms_1.repos.LectorRepository;
import com.lms.lms_1.repos.LibroRepository;
import com.lms.lms_1.service.LibroPrestadoService;
import com.lms.lms_1.util.CustomCollectors;
import com.lms.lms_1.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/libroPrestados")
public class LibroPrestadoController {

    private final LibroPrestadoService libroPrestadoService;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;

    public LibroPrestadoController(final LibroPrestadoService libroPrestadoService,
            final LibroRepository libroRepository, final LectorRepository lectorRepository) {
        this.libroPrestadoService = libroPrestadoService;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("libroValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getNombre)));
        model.addAttribute("lectorValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("libroPrestadoes", libroPrestadoService.findAll());
        return "libroPrestado/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("libroPrestado") final LibroPrestadoDTO libroPrestadoDTO) {
        return "libroPrestado/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("libroPrestado") @Valid final LibroPrestadoDTO libroPrestadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "libroPrestado/add";
        }
        libroPrestadoService.create(libroPrestadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libroPrestado.create.success"));
        return "redirect:/libroPrestados";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("libroPrestado", libroPrestadoService.get(id));
        return "libroPrestado/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("libroPrestado") @Valid final LibroPrestadoDTO libroPrestadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "libroPrestado/edit";
        }
        libroPrestadoService.update(id, libroPrestadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libroPrestado.update.success"));
        return "redirect:/libroPrestados";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        libroPrestadoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("libroPrestado.delete.success"));
        return "redirect:/libroPrestados";
    }

}
