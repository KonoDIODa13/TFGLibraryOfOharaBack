package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Genero;

public record GeneroDTO(String genero) {

    public Genero DTOtoModel() {
        Genero genero = new Genero();
        genero.setGenero(this.genero);
        return genero;
    }

    public GeneroDTO ModeltoDTO(Genero genero) {
        return new GeneroDTO(genero.getGenero());
    }
}
