package com.lms.lms_1.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LectorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String estado;

    @Size(max = 255)
    private String descripcion;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @Size(max = 255)
    private String email;

    private Boolean prestamoActivo;

}
