package com.example.tfglibraryofohara.Controllers;


import com.example.tfglibraryofohara.DTOS.GeneroDTO;
import com.example.tfglibraryofohara.Entities.Genero;
import com.example.tfglibraryofohara.Entities.Libro;
import com.example.tfglibraryofohara.Services.GeneroServicio;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/genero")
@Tag(name = "Géneros", description = "Controlador para todas las acciones de Géneros.")
public class GeneroController {
    @Autowired
    private GeneroServicio generoServicio;

    @GetMapping("/todos")
    @Operation(summary = "Listar todos los Géneros (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Géneros.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Genero.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> listarTodos() {
        return !generoServicio.listarTodos().isEmpty() ?
                new ResponseEntity<>(generoServicio.listarTodos(), HttpStatus.OK) :
                new ResponseEntity<>("No hay Géneros en la bd.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idGenero}")
    @Operation(summary = "Buscar un Género por su ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará un Género cuyo id sea ese.",
                    content = @Content(schema = @Schema(implementation = Genero.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Género con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXID(@PathVariable int idGenero) {
        return generoServicio.buscarXID(idGenero).isPresent() ?
                new ResponseEntity<>(generoServicio.buscarXID(idGenero).get(), HttpStatus.OK) :
                new ResponseEntity<>("No existe un Género con dicho ID.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idGenero}/libros")
    @Operation(summary = "Buscar un Género por su ID (si hay) y mostrar los Libros con dicho Género (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará los libros del Género en cuestión.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Género no tiene libros todavia.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Género con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> mostrarLibrosAutorXID(@PathVariable int idGenero) {
        Optional<Genero> optGenero = generoServicio.buscarXID(idGenero);
        if (optGenero.isPresent()) {
            Genero genero = optGenero.get();
            return genero.getLibros().size() != 0 ?
                    new ResponseEntity<>(genero.getLibros(), HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Género no tiene libros todavia.", HttpStatus.CONFLICT);//409
        } else {
            return new ResponseEntity<>("No existe un Género con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @PostMapping("/registrar")
    @Operation(summary = "Añadir un nuevo Género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Género con los datos pasados.",
                    content = @Content(schema = @Schema(implementation = Genero.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Género ya existe en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> registrar(@RequestBody GeneroDTO generoDTO) {
        Genero genero = generoServicio.insertar(generoDTO);
        return genero != null ?
                new ResponseEntity<>(genero, HttpStatus.OK) :
                new ResponseEntity<>("Dicho Género ya existe en la bd.", HttpStatus.CONFLICT);
    }

    @PutMapping("/{idGenero}/modificar")
    @Operation(summary = "Modificará el Género especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Modificará correctamente el Género y lo mostrará mostrará.",
                    content = @Content(schema = @Schema(implementation = Genero.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Género no se ha modificado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Género con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> modificar(@PathVariable int idGenero, @RequestBody GeneroDTO generoDTO) {
        Optional<Genero> optGenero = generoServicio.buscarXID(idGenero);
        if (optGenero.isPresent()) {
            Genero genero = optGenero.get();
            return generoServicio.modificar(genero, generoDTO) ?
                    new ResponseEntity<>(genero, HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Género no se ha modificado.", HttpStatus.NOT_MODIFIED);//304
        } else {
            return new ResponseEntity<>("No existe un Género con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @DeleteMapping("/{idGenero}/eliminar")
    @Operation(summary = "Eliminará el Género especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Género eliminado con exito.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Género no se ha eliminado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Género con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> eliminar(@PathVariable int idGenero) {
        Optional<Genero> optGenero = generoServicio.buscarXID(idGenero);
        if (optGenero.isPresent()) {
            Genero genero = optGenero.get();
            return generoServicio.eliminar(genero) ?
                    new ResponseEntity<>("Género eliminado con exito.", HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Género no se ha eliminado.", HttpStatus.CONFLICT);//304
        } else {
            return new ResponseEntity<>("No existe un Género con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

}


