package com.example.tfglibraryofohara.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/*
-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Autor`
-- -----------------------------------------------------

CREATE TABLE Autor
(
    id        INT NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(30) DEFAULT NULL,
    apellidos VARCHAR(50) DEFAULT NULL,
    edad      INT(3) DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "edad")
    private int edad;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Libro> libros;


}
