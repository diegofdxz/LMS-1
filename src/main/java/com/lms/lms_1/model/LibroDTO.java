package com.lms.lms_1.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibroDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String estado;

    @Size(max = 255)
    private String descripcion;

    @Size(max = 255)
    private String modificadoPor;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    @Size(max = 255)
    private String creadoPor;

    @Size(max = 255)
    private String autor;

}
