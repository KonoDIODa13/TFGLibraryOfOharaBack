package com.example.tfglibraryofohara.Services;

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
}
