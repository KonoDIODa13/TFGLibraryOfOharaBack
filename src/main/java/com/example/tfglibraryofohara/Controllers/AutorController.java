package com.example.tfglibraryofohara.Controllers;

import com.example.tfglibraryofohara.Services.AutorServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autor")
@Tag(name = "Autores", description = "Controlador para todas las acciones de autores")
public class AutorController {
    @Autowired
    private AutorServicio autorServicio;


    @GetMapping("/todos")
    @Operation(summary = "listar todos los Autores (si hay)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",)
    })
    public ResponseEntity<?> listarTodos() {
        return autorServicio.listarTodos().size() > 0 ?
                new ResponseEntity<>(autorServicio.listarTodos(), HttpStatus.OK) : // 200
                new ResponseEntity<>("No hay autores en la bd", HttpStatus.NO_CONTENT);//204

    }
}
