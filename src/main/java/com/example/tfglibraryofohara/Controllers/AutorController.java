package com.example.tfglibraryofohara.Controllers;

import com.example.tfglibraryofohara.DTOS.AutorDTO;
import com.example.tfglibraryofohara.Models.Autor;
import com.example.tfglibraryofohara.Services.AutorServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autor")
@Tag(name = "Autores", description = "Controlador para todas las acciones de autores.")
public class AutorController {
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/todos")
    @Operation(summary = "Listar todos los Autores (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Autores.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Autor.class)))
            ),
            @ApiResponse(responseCode = "204",
                    description = "No hay Autores en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> listarTodos() {
        return !autorServicio.listarTodos().isEmpty() ?
                new ResponseEntity<>(autorServicio.listarTodos(), HttpStatus.OK) : // 200
                new ResponseEntity<>("No hay autores en la bd", HttpStatus.NO_CONTENT);//204

    }

    @GetMapping("/{idAutor}/mostrar")
    @Operation(summary = "Buscar un Autor por su ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará un Autor cuyo id sea ese.",
                    content = @Content(schema = @Schema(implementation = Autor.class))
            ),
            @ApiResponse(responseCode = "204",
                    description = "No existe un autor con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> buscarXID(@PathVariable int idAutor) {
        return autorServicio.buscarXID(idAutor).isPresent() ?
                new ResponseEntity<>(autorServicio.buscarXID(idAutor).get(), HttpStatus.OK) :
                new ResponseEntity<>("No existe un autor con dicho ID.", HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Autor con los datos pasados.",
                    content = @Content(schema = @Schema(implementation = Autor.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho autor ya existe en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/registrar")
    @Operation(summary = "Añadir un nuevo Autor")
    public ResponseEntity<?> registrar(@RequestBody AutorDTO autorDTO) {
        Autor autor = autorServicio.insertar(autorDTO);
        return autor != null ?
                new ResponseEntity<>(autor, HttpStatus.OK) :
                new ResponseEntity<>("Dicho autor ya existe en la bd.", HttpStatus.CONFLICT)
                ;
    }
}
