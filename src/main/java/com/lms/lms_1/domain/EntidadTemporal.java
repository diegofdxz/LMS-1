package com.lms.lms_1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public abstract class EntidadTemporal extends EntidadBase {

    @Column
    private LocalDateTime fechaInicio;

    @Column
    private LocalDateTime fechaFin;

}
