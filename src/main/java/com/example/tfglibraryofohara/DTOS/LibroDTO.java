package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Libro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private String titulo;
    private String sinopsis;
    private LocalDate fechaPublicacion;
    private String portada;
    private int idAutor;
    private int idGenero;


    public Libro DTOtoModel() {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setSinopsis(sinopsis);
        libro.setFechaPublicacion(fechaPublicacion);
        libro.setPortada(portada);
        return libro;
    }

    public LibroDTO modeltoDTO(Libro libro) {
        return new LibroDTO(libro.getTitulo(),
                libro.getSinopsis(),
                libro.getFechaPublicacion(),
                libro.getPortada(),
                libro.getAutor().getId(),
                libro.getGenero().getId()
        );
    }
}
