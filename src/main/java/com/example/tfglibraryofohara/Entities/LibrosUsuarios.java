package com.example.tfglibraryofohara.Entities;


import com.example.tfglibraryofohara.Entities.Enums.Estado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Libros_Usuarios")
public class LibrosUsuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @JsonIgnoreProperties({"librosUsuarios"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_libro", referencedColumnName = "id")
    @JsonIgnoreProperties({"librosUsuarios"})
    private Libro libro;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio = LocalDate.now();

    @Enumerated()
    @Column(name = "estado")
    private Estado estado = Estado.SIN_EMPEZAR;
}
