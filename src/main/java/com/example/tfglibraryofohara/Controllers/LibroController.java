package com.example.tfglibraryofohara.Controllers;


import com.example.tfglibraryofohara.DTOS.LibroDTO;
import com.example.tfglibraryofohara.Entities.Libro;
import com.example.tfglibraryofohara.Services.LibroService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libro")
@Tag(name = "Libros", description = "Controlador para todas las acciones de Libros.")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping("/todos")
    @Operation(summary = "Listar todos los Libros (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Libros.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> listarTodos() {
        return !libroService.listarTodos().isEmpty() ?
                new ResponseEntity<>(libroService.listarTodos(), HttpStatus.OK) :
                new ResponseEntity<>("No hay Libros en la bd.", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/filtrar/genero/{genero}")
    @Operation(summary = "Listar los Libros mediante el Género puesto por parámetro (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Libros que pertenezcan a dicho Género.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros pertenecientes a dicho Género.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXGenero(@PathVariable String genero) {
        List<Libro> listaLibros = libroService.filtrarX("genero", genero);
        return !listaLibros.isEmpty() ?
                new ResponseEntity<>(listaLibros, HttpStatus.OK) :
                new ResponseEntity<>("No hay Libros pertenecientes a dicho Género.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filtrar/autor/{autor}")
    @Operation(summary = "Listar los Libros mediante el Autor puesto por parámetro (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Libros que pertenezcan a dicho Autor.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros pertenecientes a dicho Autor.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXAutor(@PathVariable String autor) {
        List<Libro> listaLibros = libroService.filtrarX("autor", autor);
        return !listaLibros.isEmpty() ?
                new ResponseEntity<>(listaLibros, HttpStatus.OK) :
                new ResponseEntity<>("No hay Libros pertenecientes a dicho Autor.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filtrar/desde/{fecha}")
    @Operation(summary = "Listar los Libros que se hayan sido publicados después de la fecha puesta por parámetro (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Libros que hayan sido publicados después de la fecha indicada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros que hayan sido publicados después de la fecha indicada.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXFechaPublicacionDesde(@PathVariable LocalDate fecha) {
        List<Libro> listaLibros = libroService.filtrarX("desde", fecha);
        return !listaLibros.isEmpty() ?
                new ResponseEntity<>(listaLibros, HttpStatus.OK) :
                new ResponseEntity<>("No hay Libros que hayan sido publicados después de la fecha indicada.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filtrar/hasta/{fecha}")
    @Operation(summary = "Listar los Libros que se hayan sido publicados antes de la fecha puesta por parámetro (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Libros que hayan sido publicados antes de la fecha indicada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Libro.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Libros que hayan sido publicados antes de la fecha inidicada.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXFechaPublicacionHasta(@PathVariable LocalDate fecha) {
        List<Libro> listaLibros = libroService.filtrarX("hasta", fecha);
        return !listaLibros.isEmpty() ?
                new ResponseEntity<>(listaLibros, HttpStatus.OK) :
                new ResponseEntity<>("No hay Libros que hayan sido publicados antes de la fecha indicada.", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{idLibro}")
    @Operation(summary = "Buscar un Libros por su ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará un Libros cuyo id sea ese.",
                    content = @Content(schema = @Schema(implementation = Libro.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Libros con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXID(@PathVariable int idLibro) {
        return libroService.buscarXID(idLibro).isPresent() ?
                new ResponseEntity<>(libroService.buscarXID(idLibro).get(), HttpStatus.OK) :
                new ResponseEntity<>("No existe un Género con dicho ID.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Añadir un nuevo Libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Libro con los datos pasados.",
                    content = @Content(schema = @Schema(implementation = Libro.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Género ya existe en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> registrar(@RequestBody LibroDTO libroDTO) {
        Libro libro = libroService.insertar(libroDTO);
        return libro != null ?
                new ResponseEntity<>(libro, HttpStatus.OK) :
                new ResponseEntity<>("Dicho Libros ya existe en la bd.", HttpStatus.CONFLICT);
    }

    @PutMapping("/{idLibro}/modificar")
    @Operation(summary = "Modificará el Libros especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Modificará correctamente el Libros y lo mostrará mostrará.",
                    content = @Content(schema = @Schema(implementation = Libro.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Libros no se ha modificado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Libros con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> modificar(@PathVariable int idLibro, @RequestBody LibroDTO libroDTO) {
        Optional<Libro> optLibro = libroService.buscarXID(idLibro);
        if (optLibro.isPresent()) {
            Libro libro = optLibro.get();
            return libroService.modificar(libro, libroDTO) ?
                    new ResponseEntity<>(libro, HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Libro no se ha modificado.", HttpStatus.NOT_MODIFIED);//304
        } else {
            return new ResponseEntity<>("No existe un Libro con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @DeleteMapping("/{idLibro}/eliminar")
    @Operation(summary = "Eliminará el Libro especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Libro eliminado con exito.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Libro no se ha eliminado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Libro con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> eliminar(@PathVariable int idLibro) {
        Optional<Libro> optLibro = libroService.buscarXID(idLibro);
        if (optLibro.isPresent()) {
            Libro libro = optLibro.get();
            return libroService.eliminar(libro) ?
                    new ResponseEntity<>("Libro eliminado con exito.", HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Libro no se ha eliminado.", HttpStatus.CONFLICT);//304
        } else {
            return new ResponseEntity<>("No existe un Libro con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

}
