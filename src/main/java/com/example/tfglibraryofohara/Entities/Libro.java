package com.example.tfglibraryofohara.Entities;

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
    autorDTO             INT NOT NULL,
    generoDTO            INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autorDTO) REFERENCES Autor (id),
    FOREIGN KEY (generoDTO) REFERENCES Genero (id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<LibrosUsuarios> getLibrosUsuarios() {
        return librosUsuarios;
    }

    public void setLibrosUsuarios(List<LibrosUsuarios> librosUsuarios) {
        this.librosUsuarios = librosUsuarios;
    }
}
