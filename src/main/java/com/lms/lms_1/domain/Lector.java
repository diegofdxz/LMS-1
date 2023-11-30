package com.lms.lms_1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Lectors")
@Getter
@Setter
public class Lector extends EntidadTemporal {

    @Column
    private String email;

    @Column
    private Boolean prestamoActivo;

    @OneToMany(mappedBy = "lector")
    private Set<LibroPrestado> librosPrestados;

    @OneToMany(mappedBy = "lector")
    private Set<Prestamo> prestamos;

}
