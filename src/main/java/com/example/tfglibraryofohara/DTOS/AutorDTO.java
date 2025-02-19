package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Models.Autor;

public record AutorDTO(String nombre, String apellidos, int edad) {

    Autor DTOtoModel() {
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellidos(apellidos);
        autor.setEdad(edad);
        return autor;
    }
}
