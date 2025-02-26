package com.example.tfglibraryofohara.Controllers;

import com.example.tfglibraryofohara.DTOS.AutorDTO;
import com.example.tfglibraryofohara.Entities.Autor;
import com.example.tfglibraryofohara.Entities.Libro;
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

import java.util.List;
import java.util.Optional;

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
            @ApiResponse(responseCode = "404",
                    description = "No hay Autores en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> listarTodos() {
        List<Autor> autores = autorServicio.listarTodos();
        return !autores.isEmpty() ?
                new ResponseEntity<>(autores, HttpStatus.OK) : // 200
                new ResponseEntity<>("No hay autores en la bd", HttpStatus.NOT_FOUND);//204
        // NO_CONTENT no me da la respuesta esperada.
    }

    @GetMapping("/{idAutor}")
    @Operation(summary = "Buscar un Autor por su ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará un Autor cuyo id sea ese.",
                    content = @Content(schema = @Schema(implementation = Autor.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Autor con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> buscarXID(@PathVariable int idAutor) {
        return autorServicio.buscarXID(idAutor).isPresent() ?
                new ResponseEntity<>(autorServicio.buscarXID(idAutor).get(), HttpStatus.OK) :
                new ResponseEntity<>("No existe un Autor con dicho ID.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idAutor}/libros")
    @Operation(summary = "Buscar un Autor por su ID (si hay) y mostrar los Libros con dicho Autor (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará los libros del Autor en cuestión.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Autor no tiene libros todavia.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Autor con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> mostrarLibrosAutorXID(@PathVariable int idAutor) {
        Optional<Autor> optAutor = autorServicio.buscarXID(idAutor);
        if (optAutor.isPresent()) {
            Autor autor = optAutor.get();
            return autor.getLibros().size() != 0 ?
                    new ResponseEntity<>(autor.getLibros(), HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Autor no tiene libros todavia.", HttpStatus.CONFLICT);//409
        } else {
            return new ResponseEntity<>("No existe un Autor con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @PostMapping("/registrar")
    @Operation(summary = "Añadir un nuevo Autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Autor con los datos pasados.",
                    content = @Content(schema = @Schema(implementation = Autor.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Autor ya existe en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> registrar(@RequestBody AutorDTO autorDTO) {
        Autor autor = autorServicio.insertar(autorDTO);
        return autor != null ?
                new ResponseEntity<>(autor, HttpStatus.OK) :
                new ResponseEntity<>("Dicho Autor ya existe en la bd.", HttpStatus.CONFLICT);
    }

    @PutMapping("/{idAutor}/modificar")
    @Operation(summary = "Modificará el Autor especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Modificará correctamente el Autor y lo mostrará mostrará.",
                    content = @Content(schema = @Schema(implementation = Autor.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Autor no se ha modificado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Autor con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> modificar(@PathVariable int idAutor, @RequestBody AutorDTO autorDTO) {
        Optional<Autor> optAutor = autorServicio.buscarXID(idAutor);
        if (optAutor.isPresent()) {
            Autor autor = optAutor.get();
            return autorServicio.modificar(autor, autorDTO) ?
                    new ResponseEntity<>(autor, HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Autor no se ha modificado.", HttpStatus.NOT_MODIFIED);//304
        } else {
            return new ResponseEntity<>("No existe un Autor con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @DeleteMapping("/{idAutor}/eliminar")
    @Operation(summary = "Eliminará el Autor especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Autor eliminado con exito.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Autor no se ha eliminado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Autor con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> eliminar(@PathVariable int idAutor) {
        Optional<Autor> optAutor = autorServicio.buscarXID(idAutor);
        if (optAutor.isPresent()) {
            Autor autor = optAutor.get();
            return autorServicio.eliminar(autor) ?
                    new ResponseEntity<>("Autor eliminado con exito.", HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Autor no se ha eliminado.", HttpStatus.CONFLICT);//304
        } else {
            return new ResponseEntity<>("No existe un Autor con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }
}
