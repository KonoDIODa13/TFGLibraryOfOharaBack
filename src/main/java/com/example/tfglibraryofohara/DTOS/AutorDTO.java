package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Autor;

public record AutorDTO(String nombre, String apellidos, int edad) {

    public Autor DTOtoModel() {
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellidos(apellidos);
        autor.setEdad(edad);
        return autor;
    }

    public AutorDTO ModeltoDTO(Autor autor) {
        return new AutorDTO(autor.getNombre(), autor.getApellidos(), autor.getEdad());
    }
}
