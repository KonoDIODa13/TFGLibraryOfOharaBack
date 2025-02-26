package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Libro;

import java.time.LocalDate;

public record LibroDTO(String titulo, String sinopsis, LocalDate fechaPublicacion, String portada
        , int idAutor, int idGenero
) {
    public Libro DTOtoModel() {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setSinopsis(sinopsis);
        libro.setFechaPublicacion(fechaPublicacion);
        libro.setPortada(portada);
        /*libro.setAutor(autorDTO.DTOtoModel());
        libro.setGenero(generoDTO.DTOtoModel());*/
        return libro;
    }

    public LibroDTO modeltoDTO(Libro libro) {
        return new LibroDTO(libro.getTitulo(), libro.getSinopsis(), libro.getFechaPublicacion(), libro.getPortada(), libro.getAutor().getId(), libro.getGenero().getId());

    }
}
