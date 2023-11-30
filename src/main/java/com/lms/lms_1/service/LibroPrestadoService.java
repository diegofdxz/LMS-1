package com.lms.lms_1.service;

import com.lms.lms_1.domain.Lector;
import com.lms.lms_1.domain.Libro;
import com.lms.lms_1.domain.LibroPrestado;
import com.lms.lms_1.model.LibroPrestadoDTO;
import com.lms.lms_1.repos.LectorRepository;
import com.lms.lms_1.repos.LibroPrestadoRepository;
import com.lms.lms_1.repos.LibroRepository;
import com.lms.lms_1.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LibroPrestadoService {

    private final LibroPrestadoRepository libroPrestadoRepository;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;

    public LibroPrestadoService(final LibroPrestadoRepository libroPrestadoRepository,
            final LibroRepository libroRepository, final LectorRepository lectorRepository) {
        this.libroPrestadoRepository = libroPrestadoRepository;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
    }

    public List<LibroPrestadoDTO> findAll() {
        final List<LibroPrestado> libroPrestadoes = libroPrestadoRepository.findAll(Sort.by("id"));
        return libroPrestadoes.stream()
                .map(libroPrestado -> mapToDTO(libroPrestado, new LibroPrestadoDTO()))
                .toList();
    }

    public LibroPrestadoDTO get(final Long id) {
        return libroPrestadoRepository.findById(id)
                .map(libroPrestado -> mapToDTO(libroPrestado, new LibroPrestadoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibroPrestadoDTO libroPrestadoDTO) {
        final LibroPrestado libroPrestado = new LibroPrestado();
        mapToEntity(libroPrestadoDTO, libroPrestado);
        return libroPrestadoRepository.save(libroPrestado).getId();
    }

    public void update(final Long id, final LibroPrestadoDTO libroPrestadoDTO) {
        final LibroPrestado libroPrestado = libroPrestadoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroPrestadoDTO, libroPrestado);
        libroPrestadoRepository.save(libroPrestado);
    }

    public void delete(final Long id) {
        libroPrestadoRepository.deleteById(id);
    }

    private LibroPrestadoDTO mapToDTO(final LibroPrestado libroPrestado,
            final LibroPrestadoDTO libroPrestadoDTO) {
        libroPrestadoDTO.setId(libroPrestado.getId());
        libroPrestadoDTO.setFechaInicioPrestamo(libroPrestado.getFechaInicioPrestamo());
        libroPrestadoDTO.setFechaFinPrestamo(libroPrestado.getFechaFinPrestamo());
        libroPrestadoDTO.setEstadoPrestamo(libroPrestado.getEstadoPrestamo());
        libroPrestadoDTO.setLibro(libroPrestado.getLibro() == null ? null : libroPrestado.getLibro().getId());
        libroPrestadoDTO.setLector(libroPrestado.getLector() == null ? null : libroPrestado.getLector().getId());
        return libroPrestadoDTO;
    }

    private LibroPrestado mapToEntity(final LibroPrestadoDTO libroPrestadoDTO,
            final LibroPrestado libroPrestado) {
        libroPrestado.setFechaInicioPrestamo(libroPrestadoDTO.getFechaInicioPrestamo());
        libroPrestado.setFechaFinPrestamo(libroPrestadoDTO.getFechaFinPrestamo());
        libroPrestado.setEstadoPrestamo(libroPrestadoDTO.getEstadoPrestamo());
        final Libro libro = libroPrestadoDTO.getLibro() == null ? null : libroRepository.findById(libroPrestadoDTO.getLibro())
                .orElseThrow(() -> new NotFoundException("libro not found"));
        libroPrestado.setLibro(libro);
        final Lector lector = libroPrestadoDTO.getLector() == null ? null : lectorRepository.findById(libroPrestadoDTO.getLector())
                .orElseThrow(() -> new NotFoundException("lector not found"));
        libroPrestado.setLector(lector);
        return libroPrestado;
    }

}
