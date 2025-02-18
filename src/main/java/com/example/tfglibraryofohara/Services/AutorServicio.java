package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.Repositorys.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepository autorRepository;
}
