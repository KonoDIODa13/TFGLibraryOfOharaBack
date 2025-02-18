package com.example.tfglibraryofohara.Controllers;

import com.example.tfglibraryofohara.Services.AutorServicio;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autor")
@Tag(name = "Autores", description = "Controlador para todas las acciones de autores")
public class AutorController {
    @Autowired
    private AutorServicio autorServicio;
}
