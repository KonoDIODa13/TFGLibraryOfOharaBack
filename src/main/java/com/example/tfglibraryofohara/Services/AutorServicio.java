package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.Models.Autor;
import com.example.tfglibraryofohara.Repositorys.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }
}
