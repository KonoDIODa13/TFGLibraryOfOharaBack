package com.example.tfglibraryofohara.Services;

import com.example.tfglibraryofohara.DTOS.UsuarioDTO;
import com.example.tfglibraryofohara.Entities.Enums.Estado;
import com.example.tfglibraryofohara.Entities.Libro;
import com.example.tfglibraryofohara.Entities.LibrosUsuarios;
import com.example.tfglibraryofohara.Entities.Usuario;
import com.example.tfglibraryofohara.Repositorys.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibrosUsuariosRepository librosUsuariosRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarXID(int idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }


    public Usuario insertar(UsuarioDTO usuarioDTO) {
        Usuario usuario = null;
        if (!comprobar(usuarioDTO)) {
            usuario = usuarioDTO.DTOtoModel();
            save(usuario);
        }
        return usuario;
    }

    public boolean modificar(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuarioDTO.codificarContra();
        boolean modificado = false;

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setGmail(usuarioDTO.getGmail());
        usuario.setContrasenna(usuarioDTO.getContrasenna());
        usuario.setRol(usuarioDTO.getRol());
        modificado = true;
        save(usuario);
        return modificado;
    }

    public boolean eliminar(Usuario usuario) {
        boolean eliminado = false;
        int preEliminado = listarTodos().size();
        delete(usuario);
        int postEliminado = listarTodos().size();
        if (preEliminado > postEliminado) {
            eliminado = true;
        }
        return eliminado;
    }

    public <S extends Usuario> S save(S entity) {
        return usuarioRepository.save(entity);
    }

    public void delete(Usuario entity) {
        usuarioRepository.delete(entity);
    }

    private boolean comprobar(UsuarioDTO usuarioDTO) {
        boolean existe = false;
        Usuario optUsuario = listarTodos().stream()
                .filter(usuario ->
                        usuario.getNombre().equalsIgnoreCase(usuarioDTO.getNombre()) &&
                                usuario.getContrasenna().equalsIgnoreCase(usuarioDTO.getContrasenna()))
                .findFirst().orElse(null);
        if (optUsuario != null) {
            existe = true;
        }
        return existe;
    }

    // libroUsuario

    public List<LibrosUsuarios> listarLibros() {
        return librosUsuariosRepository.findAll();
    }

    public List<LibrosUsuarios> listarLibrosByUsuario(int idUsuario) {
        return listarLibros().stream()
                .filter(librosUsuarios -> librosUsuarios.getUsuario().getId() == idUsuario).toList();
    }

    public Usuario login(String nombre, String contrasenna) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre)
                        && usuario.getContrasenna().equalsIgnoreCase(DigestUtils.sha256Hex(contrasenna))
                ).findFirst().orElse(null);
    }

    public Usuario registro(UsuarioDTO usuarioDTO) {
        usuarioDTO.codificarContra();
        return insertar(usuarioDTO);
    }

    public List<LibrosUsuarios> getLibrosByUsuario(int idUsuario) {
        return listarLibros().stream().filter(librosUsuarios -> librosUsuarios.getUsuario().getId() == idUsuario).toList();
    }


    public LibrosUsuarios insertarLibro(int idUsuario, int idLibro) {
        /*List<LibrosUsuarios> todosLibrosUsuarios = librosUsuariosRepository.findAll();

        if (todosLibrosUsuarios.stream()
                .anyMatch(librosUsuarios ->
                        librosUsuarios.getUsuario().getId() == usuario.getId()
                                && librosUsuarios.getLibro().getId() == libro.getId())) {
            return null;
        }

        LibrosUsuarios librosUsuarios = new LibrosUsuarios();
        librosUsuarios.setUsuario(usuario);
        librosUsuarios.setLibro(libro);
        librosUsuarios.setEstado(Estado.SIN_EMPEZAR);
        librosUsuarios.setFechaInicio(LocalDate.now());
        System.out.println(librosUsuarios);
        return librosUsuariosRepository.save(librosUsuarios);*/

        Usuario usuario = usuarioRepository.getById(idUsuario);
        Libro libro = libroRepository.getById(idLibro);

        LibrosUsuarios librosUsuarios = new LibrosUsuarios();
        librosUsuarios.setUsuario(usuario);
        librosUsuarios.setLibro(libro);
        librosUsuarios.setEstado(Estado.SIN_EMPEZAR);
        librosUsuarios.setFechaInicio(LocalDate.now());
        return librosUsuariosRepository.save(librosUsuarios);
    }


    public int modificarEstado(LibrosUsuarios librosUsuarios) {
        if (buscarXID(librosUsuarios.getUsuario().getId()).isEmpty()) {
            return 3;
        }
        if (!libroRepository.existsById(librosUsuarios.getLibro().getId())) {
            return 2;
        }
        LibrosUsuarios libroUsuario = librosUsuariosRepository.getById(librosUsuarios.getId());
        libroUsuario.setEstado(librosUsuarios.getEstado());
        librosUsuariosRepository.save(libroUsuario);
        return 1;
    }

    @Transactional
    public Integer eliminarLibro(int idUsuario, int idLibro) {
        if (buscarXID(idUsuario).isPresent()) {
            if (libroRepository.existsById(idLibro)) {
                Optional<LibrosUsuarios> existeLibroOpt = librosUsuariosRepository.findByUsuario_IdAndLibro_Id(idUsuario, idLibro);
                if (existeLibroOpt.isPresent()) {
                    System.out.println(existeLibroOpt.get());
                    librosUsuariosRepository.deleteById(existeLibroOpt.get().getId());
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 3;
            }
        } else {
            return 4;
        }
    }
        /*if (buscarXID(idUsuario).isPresent()) {
            if (libroRepository.existsById(idLibro)) {
                LibrosUsuarios existeLibro = librosUsuariosRepository.findAll()
                        .stream()
                        .filter(librosUsuarios -> librosUsuarios.getUsuario().getId() == idUsuario &&
                                librosUsuarios.getLibro().getId() == idLibro)
                        .findFirst().orElse(null);
                if (existeLibro != null) {
                    librosUsuariosRepository.deleteById(existeLibro.getId());
                    return 1;


            } else {
                return 2;
            }
        } else {
            return 3;
        }*/

}
