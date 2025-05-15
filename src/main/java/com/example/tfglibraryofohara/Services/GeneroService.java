package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.DTOS.GeneroDTO;
import com.example.tfglibraryofohara.Entities.Genero;
import com.example.tfglibraryofohara.Repositorys.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> buscarXID(int idGenero) {
        return generoRepository.findById(idGenero);
    }

    public Genero insertar(GeneroDTO generoDTO) {
        Genero genero = null;
        if (!comprobar(generoDTO)) {
            genero = generoDTO.DTOtoModel();
            save(genero);
        }
        return genero;
    }

    public boolean modificar(Genero genero, GeneroDTO generoDTO) {
        boolean modificado = false;
        if (!genero.getGenero().equalsIgnoreCase(generoDTO.getGenero())) {
            genero.setGenero(generoDTO.getGenero());
            save(genero);
            modificado = true;
        }
        return modificado;
    }

    public boolean eliminar(Genero genero) {
        boolean eliminado = false;
        int preEliminado = listarTodos().size();
        delete(genero);
        int postEliminado = listarTodos().size();
        if (preEliminado > postEliminado) {
            eliminado = true;
        }
        return eliminado;
    }

    public <S extends Genero> S save(S entity) {
        return generoRepository.save(entity);
    }

    public void delete(Genero entity) {
        generoRepository.delete(entity);
    }

    private boolean comprobar(GeneroDTO generoDTO) {
        boolean existe = false;
        Genero optGenero = listarTodos().stream()
                .filter(genero ->
                        genero.getGenero().equalsIgnoreCase(generoDTO.getGenero()))
                .findFirst().orElse(null);
        if (optGenero != null) {
            existe = true;
        }
        return existe;
    }
}