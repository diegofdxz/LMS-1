package com.lms.lms_1.repos;

import com.lms.lms_1.domain.Lector;
import com.lms.lms_1.domain.Libro;
import com.lms.lms_1.domain.LibroPrestado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroPrestadoRepository extends JpaRepository<LibroPrestado, Long> {

    LibroPrestado findFirstByLibro(Libro libro);

    LibroPrestado findFirstByLector(Lector lector);

}
