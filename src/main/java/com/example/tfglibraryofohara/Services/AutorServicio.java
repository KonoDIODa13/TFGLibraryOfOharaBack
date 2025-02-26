package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.DTOS.AutorDTO;
import com.example.tfglibraryofohara.Entities.Autor;
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

    public boolean modificar(Autor autor, AutorDTO autorDTO) {
        boolean modificado = false;
        if (!autor.getNombre().equalsIgnoreCase(autorDTO.nombre())) {
            autor.setNombre(autorDTO.nombre());
            autor.setApellidos(autorDTO.apellidos());
            autor.setEdad(autorDTO.edad());
            modificado = true;
            save(autor);
        }
        return modificado;
    }

    public boolean eliminar(Autor autor) {
        boolean eliminado = false;
        int preEliminado = listarTodos().size();
        delete(autor);
        int postEliminado = listarTodos().size();
        if (preEliminado > postEliminado) {
            eliminado = true;
        }
        return eliminado;
    }


    private void delete(Autor entity) {
        autorRepository.delete(entity);
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
