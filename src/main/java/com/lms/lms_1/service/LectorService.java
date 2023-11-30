package com.lms.lms_1.service;

import com.lms.lms_1.domain.Lector;
import com.lms.lms_1.domain.LibroPrestado;
import com.lms.lms_1.domain.Prestamo;
import com.lms.lms_1.model.LectorDTO;
import com.lms.lms_1.repos.LectorRepository;
import com.lms.lms_1.repos.LibroPrestadoRepository;
import com.lms.lms_1.repos.PrestamoRepository;
import com.lms.lms_1.util.NotFoundException;
import com.lms.lms_1.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LectorService {

    private final LectorRepository lectorRepository;
    private final LibroPrestadoRepository libroPrestadoRepository;
    private final PrestamoRepository prestamoRepository;

    public LectorService(final LectorRepository lectorRepository,
            final LibroPrestadoRepository libroPrestadoRepository,
            final PrestamoRepository prestamoRepository) {
        this.lectorRepository = lectorRepository;
        this.libroPrestadoRepository = libroPrestadoRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LectorDTO> findAll() {
        final List<Lector> lectors = lectorRepository.findAll(Sort.by("id"));
        return lectors.stream()
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .toList();
    }

    public LectorDTO get(final Long id) {
        return lectorRepository.findById(id)
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LectorDTO lectorDTO) {
        final Lector lector = new Lector();
        mapToEntity(lectorDTO, lector);
        return lectorRepository.save(lector).getId();
    }

    public void update(final Long id, final LectorDTO lectorDTO) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lectorDTO, lector);
        lectorRepository.save(lector);
    }

    public void delete(final Long id) {
        lectorRepository.deleteById(id);
    }

    private LectorDTO mapToDTO(final Lector lector, final LectorDTO lectorDTO) {
        lectorDTO.setId(lector.getId());
        lectorDTO.setNombre(lector.getNombre());
        lectorDTO.setEstado(lector.getEstado());
        lectorDTO.setDescripcion(lector.getDescripcion());
        lectorDTO.setFechaInicio(lector.getFechaInicio());
        lectorDTO.setFechaFin(lector.getFechaFin());
        lectorDTO.setEmail(lector.getEmail());
        lectorDTO.setPrestamoActivo(lector.getPrestamoActivo());
        return lectorDTO;
    }

    private Lector mapToEntity(final LectorDTO lectorDTO, final Lector lector) {
        lector.setNombre(lectorDTO.getNombre());
        lector.setEstado(lectorDTO.getEstado());
        lector.setDescripcion(lectorDTO.getDescripcion());
        lector.setFechaInicio(lectorDTO.getFechaInicio());
        lector.setFechaFin(lectorDTO.getFechaFin());
        lector.setEmail(lectorDTO.getEmail());
        lector.setPrestamoActivo(lectorDTO.getPrestamoActivo());
        return lector;
    }

    public String getReferencedWarning(final Long id) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LibroPrestado lectorLibroPrestado = libroPrestadoRepository.findFirstByLector(lector);
        if (lectorLibroPrestado != null) {
            return WebUtils.getMessage("lector.libroPrestado.lector.referenced", lectorLibroPrestado.getId());
        }
        final Prestamo lectorPrestamo = prestamoRepository.findFirstByLector(lector);
        if (lectorPrestamo != null) {
            return WebUtils.getMessage("lector.prestamo.lector.referenced", lectorPrestamo.getId());
        }
        return null;
    }

}
