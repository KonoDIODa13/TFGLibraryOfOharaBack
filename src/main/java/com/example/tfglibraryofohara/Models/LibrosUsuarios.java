package com.example.tfglibraryofohara.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

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
}
