package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AutorDTO {
    private String nombre;
    private String apellidos;
    private int edad;


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
