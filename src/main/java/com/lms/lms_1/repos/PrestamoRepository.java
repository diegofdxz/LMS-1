package com.lms.lms_1.repos;

import com.lms.lms_1.domain.Lector;
import com.lms.lms_1.domain.Libro;
import com.lms.lms_1.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByLibro(Libro libro);

    Prestamo findFirstByLector(Lector lector);

}
