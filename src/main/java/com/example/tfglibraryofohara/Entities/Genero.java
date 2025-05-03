package com.example.tfglibraryofohara.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/*
-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Genero`
-- -----------------------------------------------------

CREATE TABLE Genero
(
    id     INT NOT NULL AUTO_INCREMENT,
    genero VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"libros"})
@Entity
@Table(name = "Genero")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "genero")
    private String genero;

    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Libro> libros;
}
