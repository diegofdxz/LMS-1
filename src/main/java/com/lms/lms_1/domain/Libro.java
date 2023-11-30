package com.lms.lms_1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Libroes")
@Getter
@Setter
public class Libro extends EntidadAuditable {

    @Column
    private String autor;

    @OneToMany(mappedBy = "libro")
    private Set<LibroPrestado> librosPrestados;

    @OneToMany(mappedBy = "libro")
    private Set<Prestamo> prestamos;

}
