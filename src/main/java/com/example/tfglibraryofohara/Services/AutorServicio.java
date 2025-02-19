package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.DTOS.AutorDTO;
import com.example.tfglibraryofohara.Models.Autor;
import com.example.tfglibraryofohara.Repositorys.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarXID(int idAutor) {
        return autorRepository.findById(idAutor);
    }

    public Autor insertar(AutorDTO autorDTO) {
        Autor autor = null;
        if (!comprobar(autorDTO)) {
            autor = autorDTO.DTOtoModel();
            save(autor);
        }
        return autor;
    }

    private <S extends Autor> S save(S entity) {
        return autorRepository.save(entity);
    }

    private boolean comprobar(AutorDTO autorDTO) {
        boolean existe = false;
        Autor optAutor = listarTodos().stream().filter(autor -> autor.getNombre().equalsIgnoreCase(autorDTO.nombre()) &&
                autor.getApellidos().equalsIgnoreCase(autorDTO.apellidos()) &&
                autor.getEdad() == autorDTO.edad()).findFirst().orElse(null);
        if (optAutor != null) {
            existe = true;
        }
        return existe;
    }
}
