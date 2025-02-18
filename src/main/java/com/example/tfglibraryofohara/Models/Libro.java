package com.example.tfglibraryofohara.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/*
-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Libro`
-- -----------------------------------------------------

CREATE TABLE Libro
(
    id                INT NOT NULL AUTO_INCREMENT,
    titulo            VARCHAR(50)   DEFAULT NULL,
    sinopsis          VARCHAR(1000) DEFAULT NULL,
    fecha_publicacion DATE          DEFAULT NULL,
    portada           VARCHAR(500)  DEFAULT NULL,
    autor             INT NOT NULL,
    genero            INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor) REFERENCES Autor (id),
    FOREIGN KEY (genero) REFERENCES Genero (id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "portada")
    private String portada;

    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "id")
    @JsonBackReference
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "genero", referencedColumnName = "id")
    @JsonBackReference
    private Genero genero;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LibrosUsuarios> librosUsuarios;
}
