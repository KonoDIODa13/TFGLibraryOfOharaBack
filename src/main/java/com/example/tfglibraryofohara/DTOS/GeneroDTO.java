package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GeneroDTO {
    private String genero;

    public Genero DTOtoModel() {
        Genero genero = new Genero();
        genero.setGenero(getGenero());
        return genero;
    }

    public GeneroDTO ModeltoDTO(Genero genero) {
        return new GeneroDTO(genero.getGenero());
    }

}
