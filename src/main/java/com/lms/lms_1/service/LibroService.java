package com.lms.lms_1.service;

import com.lms.lms_1.domain.Libro;
import com.lms.lms_1.domain.LibroPrestado;
import com.lms.lms_1.domain.Prestamo;
import com.lms.lms_1.model.LibroDTO;
import com.lms.lms_1.repos.LibroPrestadoRepository;
import com.lms.lms_1.repos.LibroRepository;
import com.lms.lms_1.repos.PrestamoRepository;
import com.lms.lms_1.util.NotFoundException;
import com.lms.lms_1.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final LibroPrestadoRepository libroPrestadoRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroService(final LibroRepository libroRepository,
            final LibroPrestadoRepository libroPrestadoRepository,
            final PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.libroPrestadoRepository = libroPrestadoRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("id"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final Long id) {
        return libroRepository.findById(id)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        return libroRepository.save(libro).getId();
    }

    public void update(final Long id, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final Long id) {
        libroRepository.deleteById(id);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setId(libro.getId());
        libroDTO.setNombre(libro.getNombre());
        libroDTO.setEstado(libro.getEstado());
        libroDTO.setDescripcion(libro.getDescripcion());
        libroDTO.setModificadoPor(libro.getModificadoPor());
        libroDTO.setFechaCreacion(libro.getFechaCreacion());
        libroDTO.setFechaModificacion(libro.getFechaModificacion());
        libroDTO.setCreadoPor(libro.getCreadoPor());
        libroDTO.setAutor(libro.getAutor());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setNombre(libroDTO.getNombre());
        libro.setEstado(libroDTO.getEstado());
        libro.setDescripcion(libroDTO.getDescripcion());
        libro.setModificadoPor(libroDTO.getModificadoPor());
        libro.setFechaCreacion(libroDTO.getFechaCreacion());
        libro.setFechaModificacion(libroDTO.getFechaModificacion());
        libro.setCreadoPor(libroDTO.getCreadoPor());
        libro.setAutor(libroDTO.getAutor());
        return libro;
    }

    public String getReferencedWarning(final Long id) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LibroPrestado libroLibroPrestado = libroPrestadoRepository.findFirstByLibro(libro);
        if (libroLibroPrestado != null) {
            return WebUtils.getMessage("libro.libroPrestado.libro.referenced", libroLibroPrestado.getId());
        }
        final Prestamo libroPrestamo = prestamoRepository.findFirstByLibro(libro);
        if (libroPrestamo != null) {
            return WebUtils.getMessage("libro.prestamo.libro.referenced", libroPrestamo.getId());
        }
        return null;
    }

}
