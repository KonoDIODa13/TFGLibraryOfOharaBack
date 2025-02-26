package com.example.tfglibraryofohara.Entities;


import com.example.tfglibraryofohara.Entities.Enums.Estado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/*
-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Libros_Usuarios`
-- -----------------------------------------------------

CREATE TABLE Libros_Usuarios
(
    id        INT NOT NULL AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idLibro   INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idUsuario) REFERENCES Usuario (id),
    FOREIGN KEY (idLibro) REFERENCES Libro (id)

)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Libros_Usuarios")
public class LibrosUsuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    @JsonBackReference
    private Libro libro;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio = LocalDate.now();

    @Column(name = "estado")
    private Estado estado = Estado.SIN_EMPEZAR;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
