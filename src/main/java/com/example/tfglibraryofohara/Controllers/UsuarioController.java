package com.example.tfglibraryofohara.Controllers;

import com.example.tfglibraryofohara.DTOS.LoginDTO;
import com.example.tfglibraryofohara.DTOS.UsuarioDTO;
import com.example.tfglibraryofohara.Entities.Enums.Estado;
import com.example.tfglibraryofohara.Entities.Libro;
import com.example.tfglibraryofohara.Entities.LibrosUsuarios;
import com.example.tfglibraryofohara.Entities.Usuario;
import com.example.tfglibraryofohara.Services.LibroService;
import com.example.tfglibraryofohara.Services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuarios", description = "Controlador para todas las acciones de Usuarios.")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/todos")
    @Operation(summary = "Listar todos los Usuarios (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará la lista de Usuarios.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No hay Usuarios en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> listarTodos() {
        return !usuarioService.listarTodos().isEmpty() ?
                new ResponseEntity<>(usuarioService.listarTodos(), HttpStatus.OK) :
                new ResponseEntity<>("No hay Usuarios en la bd.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Buscar un Usuario por su ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mostrará un Usuario cuyo id sea ese.",
                    content = @Content(schema = @Schema(implementation = Libro.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> buscarXID(@PathVariable int idUsuario) {
        return usuarioService.buscarXID(idUsuario).isPresent() ?
                new ResponseEntity<>(usuarioService.buscarXID(idUsuario).get(), HttpStatus.OK) :
                new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Añadir un nuevo Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Usuario con los datos pasados.",
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Usuario ya existe en la bd.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.insertar(usuarioDTO);
        if (usuario != null) {
            String token = generarToken(usuario);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(usuario);
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dicho Usuario ya existe en la bd.");
    }

    @PutMapping("/{idUsuario}/modificar")
    @Operation(summary = "Modificará el Usuario especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Modificará correctamente el Usuario y lo mostrará.",
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Usuario no se ha modificado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> modificar(@PathVariable int idUsuario, @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> optUsuario = usuarioService.buscarXID(idUsuario);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return usuarioService.modificar(usuario, usuarioDTO) ?
                    new ResponseEntity<>(usuario, HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Usuario no se ha modificado.", HttpStatus.NOT_MODIFIED);//304
        } else {
            return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @DeleteMapping("/{idUsuario}/eliminar")
    @Operation(summary = "Eliminará el Usuario especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Usuario eliminado con exito.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "Dicho Usuario no se ha eliminado.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> eliminar(@PathVariable int idUsuario) {
        Optional<Usuario> optUsuario = usuarioService.buscarXID(idUsuario);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return usuarioService.eliminar(usuario) ?
                    new ResponseEntity<>("Usuario eliminado con exito.", HttpStatus.OK) :
                    new ResponseEntity<>("Dicho Usuario no se ha eliminado.", HttpStatus.CONFLICT);//304
        } else {
            return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);//404
        }
    }

    @PostMapping("{idUsuario}/registrarLibro/{idLibro}")
    @Operation(summary = "Añadir un nuevo Libro a la lista.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Añadirá correctamente el Libro a la lista.",
                    content = @Content(schema = @Schema(implementation = LibrosUsuarios.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Dicho Libro ya existe en la lista.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> registrarLibro(@PathVariable int idUsuario, @PathVariable int idLibro) {
        Optional<Usuario> optUsuario = usuarioService.buscarXID(idUsuario);
        Optional<Libro> optLibro = libroService.buscarXID(idLibro);
        if (optUsuario.isPresent()) {
            if (optLibro.isPresent()) {
                LibrosUsuarios librosUsuarios = usuarioService.insertarLibro(idUsuario, idLibro);
                return librosUsuarios != null ?
                        new ResponseEntity<>(librosUsuarios, HttpStatus.OK) :
                        new ResponseEntity<>("Dicho Libro ya existe en la lista.", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("No existe un Libro con dicho ID.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idUsuario}/modificarLibro")
    @Operation(summary = "Modificará el Estado del Libro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Modificará correctamente el Modificará el Estado del Libro y mostrará el cambio.",
                    content = @Content(schema = @Schema(implementation = LibrosUsuarios.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Dicho Libro no existe.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "304",
                    description = "No coincide El Usuario con el del Libro que quieres añadir.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> modificarLibro(@PathVariable int idUsuario, @RequestBody LibrosUsuarios librosUsuarios) {
        int result = usuarioService.modificarLibroUsuario(librosUsuarios);
        if (idUsuario != librosUsuarios.getUsuario().getId()) {
            return new ResponseEntity<>("No coincide El Usuario con el del Libro que quieres añadir.", HttpStatus.CONFLICT);
        }
        switch (result) {
            case 1 -> {
                return new ResponseEntity<>("Estado del Libro Modificado Correctamente", HttpStatus.OK);
            }
            case 2 -> {
                return new ResponseEntity<>("Dicho Libro no existe.", HttpStatus.NOT_FOUND);
            }
            case 3 -> {
                return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);
            }
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }

    }

    @DeleteMapping("/{idUsuario}/eliminarLibro/{idLibro}")
    @Operation(summary = "Eliminará el Libro de la lista especificado por ID (si hay).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Libro eliminado de la lista con exito.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Libro con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "No existe un Usuario con dicho ID.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> eliminarLibro(@PathVariable int idUsuario, @PathVariable int idLibro) {
        Integer result = usuarioService.eliminarLibro(idUsuario, idLibro);
        switch (result) {
            case 1 -> {
                return ResponseEntity.ok("Libro eliminado de la lista con exito.");
            }
            case 2 -> {
                return new ResponseEntity<>("No existe un Libro con dicho ID.", HttpStatus.NOT_FOUND);
            }
            case 3 -> {
                return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);
            }
            case 4 -> {
                return new ResponseEntity<>("No existe en la lista dicho Libro.", HttpStatus.NOT_FOUND);
            }
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }
    }

    @GetMapping("/login")
    @Operation(summary = "logear el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login del Usuario correcto.",
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Login del Usuario incorrecto.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        Usuario usuario = usuarioService.login(loginDTO.getNombreUsuario(), loginDTO.getContrasenna());
        if (usuario != null) {
            String token = generarToken(usuario);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(usuario);
        } else
            return ResponseEntity.ok("Login de usuario incorrecto");
    }

    @GetMapping("/{idUsuario}/libros")
    @Operation(summary = "mostrar los libros de dicho Usuario.")
    public ResponseEntity<?> listarLibrosByUsuario(@PathVariable int idUsuario) {
        return getByID(idUsuario) != null ?
                new ResponseEntity<>(usuarioService.listarLibrosByUsuario(idUsuario), HttpStatus.OK) :
                new ResponseEntity<>("Usuario no existe", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idUsuario}/libros/{idLibro}")
    @Operation(summary = "compruyeba si existe el Libro en la lista.")
    public ResponseEntity<?> existeLibroEnLista(@PathVariable int idUsuario, @PathVariable int idLibro) {
        Optional<Usuario> optUsuario = usuarioService.buscarXID(idUsuario);
        Optional<Libro> optLibro = libroService.buscarXID(idLibro);
        if (optUsuario.isPresent()) {
            if (optLibro.isPresent()) {
                if (usuarioService.comprobarSiExisteLibroEnLista(idUsuario, idLibro)) {
                    LibrosUsuarios librosUsuarios = usuarioService.getLibroUsuario(idUsuario, idLibro);
                    return new ResponseEntity<>(librosUsuarios, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("No existe un Libro con dicho ID.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No existe un Usuario con dicho ID.", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idLibroUsuario}/modificarEstado")
    public ResponseEntity<?> modificarEstadoLibro(@PathVariable int idLibroUsuario, @RequestBody Estado estado) {
        LibrosUsuarios librosUsuarios = usuarioService.listarLibros().stream().filter(libroUsuario -> libroUsuario.getId() == idLibroUsuario).findFirst().orElse(null);
        return librosUsuarios != null ?
                ResponseEntity.ok(usuarioService.modificarEstadoLibro(librosUsuarios, estado)) :
                new ResponseEntity<>("Error al modificar el estado",HttpStatus.NOT_FOUND);
    }

    public Usuario getByID(int idUsuario) {
        return usuarioService.buscarXID(idUsuario).orElse(null);
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .claim("authorities", List.of("ROLE_USER"))
                .signWith(SignatureAlgorithm.HS512, "mySecretKey".getBytes())
                .compact();
    }

}
