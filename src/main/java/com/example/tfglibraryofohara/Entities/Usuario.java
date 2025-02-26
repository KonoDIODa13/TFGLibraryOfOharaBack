package com.example.tfglibraryofohara.Entities;

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
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "contrasenna")
    private String contrasenna;

    @Column(name = "rol")
    private String rol= "USER";

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LibrosUsuarios> librosUsuarios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<LibrosUsuarios> getLibrosUsuarios() {
        return librosUsuarios;
    }

    public void setLibrosUsuarios(List<LibrosUsuarios> librosUsuarios) {
        this.librosUsuarios = librosUsuarios;
    }
}

