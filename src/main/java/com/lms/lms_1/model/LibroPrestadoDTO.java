package com.lms.lms_1.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibroPrestadoDTO {

    private Long id;

    private LocalDateTime fechaInicioPrestamo;

    private LocalDateTime fechaFinPrestamo;

    @Size(max = 255)
    private String estadoPrestamo;

    private Long libro;

    private Long lector;

}
