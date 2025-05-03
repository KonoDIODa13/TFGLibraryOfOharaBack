package com.example.tfglibraryofohara.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/*
-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE Usuario
(
    id         INT NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(30)  DEFAULT NULL,
    apellidos  VARCHAR(50)  DEFAULT NULL,
    gmail      VARCHAR(50)  DEFAULT NULL,
    contraseña VARCHAR(500) DEFAULT NULL,
    rol        BOOLEAN      DEFAULT FALSE,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;
*/

@Data
@NoArgsConstructor
@ToString(exclude = {"librosUsuarios"})
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "gmail")
    private String gmail;

    @Column(name = "contrasenna")
    private String contrasenna;

    @Column(name = "rol")
    private String rol = "USER";

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LibrosUsuarios> librosUsuarios;
}




