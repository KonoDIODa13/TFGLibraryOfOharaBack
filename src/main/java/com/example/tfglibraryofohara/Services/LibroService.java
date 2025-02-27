package com.example.tfglibraryofohara.Services;


import com.example.tfglibraryofohara.DTOS.GeneroDTO;
import com.example.tfglibraryofohara.DTOS.LibroDTO;
import com.example.tfglibraryofohara.Entities.Autor;
import com.example.tfglibraryofohara.Entities.Genero;
import com.example.tfglibraryofohara.Entities.Libro;
import com.example.tfglibraryofohara.Repositorys.AutorRepository;
import com.example.tfglibraryofohara.Repositorys.GeneroRepository;
import com.example.tfglibraryofohara.Repositorys.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private GeneroRepository generoRepository;

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Optional<Libro> buscarXID(int idLibro) {
        return libroRepository.findById(idLibro);
    }

    public <T> List<Libro> filtrarX(String tipoFiltro, T filtro) {
        List<Libro> listaFiltrada = new ArrayList<>();
        switch (tipoFiltro) {
            case "autor" -> {
                String filtroAutor = (String) filtro;
                Optional<Autor> optAutor = autorRepository.findAll().stream()
                        .filter(autor ->
                                autor.getNombre().equalsIgnoreCase(filtroAutor)
                        ).findFirst();
                if (optAutor.isPresent()) {
                    Autor autor = optAutor.get();
                    listaFiltrada = listarTodos().stream()
                            .filter(libro ->
                                    libro.getAutor() == autor
                            ).toList();
                }
            }

            case "genero" -> {
                String generoFiltro = (String) filtro;
                Optional<Genero> optGenero = generoRepository.findAll().stream()
                        .filter(autor ->
                                autor.getGenero().equalsIgnoreCase(generoFiltro)
                        ).findFirst();
                if (optGenero.isPresent()) {
                    Genero genero = optGenero.get();
                    listaFiltrada = listarTodos().stream()
                            .filter(libro ->
                                    libro.getGenero() == genero
                            ).toList();
                }
            }

            case "desde" -> {
                LocalDate filtroFecha = (LocalDate) filtro;
                listaFiltrada = listarTodos().stream()
                        .filter(libro ->
                                libro.getFechaPublicacion().isAfter(filtroFecha)
                        ).toList();
            }

            case "hasta" -> {
                LocalDate filtroFecha = (LocalDate) filtro;
                listaFiltrada = listarTodos().stream()
                        .filter(libro ->
                                libro.getFechaPublicacion().isBefore(filtroFecha)
                        ).toList();
            }

        }
        return listaFiltrada;
    }

    public Libro insertar(LibroDTO libroDTO) {
        Libro libro = null;
        Autor autor = comprobarAutor(libroDTO.idAutor());
        Genero genero = comprobarGenero(libroDTO.idGenero());
        if (!comprobar(libroDTO) && autor != null && genero != null) {
            libro = libroDTO.DTOtoModel();
            libro.setAutor(autor);
            libro.setGenero(genero);
            save(libro);
        }
        return libro;
    }

    public boolean modificar(Libro libro, LibroDTO libroDTO) {
        boolean modificado = false;
        Autor autor = comprobarAutor(libroDTO.idAutor());
        Genero genero = comprobarGenero(libroDTO.idGenero());
        if (!libro.getTitulo().equalsIgnoreCase(libroDTO.titulo()) && autor != null && genero != null) {
            libro.setTitulo(libroDTO.titulo());
            libro.setSinopsis(libroDTO.sinopsis());
            libro.setFechaPublicacion(libroDTO.fechaPublicacion());
            libro.setPortada(libroDTO.portada());
            libro.setAutor(autor);
            libro.setGenero(genero);
            modificado = true;
        }
        return modificado;
    }

    public boolean eliminar(Libro libro) {
        boolean eliminado = false;
        int preEliminado = listarTodos().size();
        delete(libro);
        int postEliminado = listarTodos().size();
        if (preEliminado > postEliminado) {
            eliminado = true;
        }
        return eliminado;
    }

    public <S extends Libro> S save(S entity) {
        return libroRepository.save(entity);
    }

    public void delete(Libro entity) {
        libroRepository.delete(entity);
    }

    private boolean comprobar(LibroDTO libroDTO) {
        boolean existe = false;
        Libro optLibro = listarTodos().stream()
                .filter(libro ->
                        libro.getTitulo().equalsIgnoreCase(libroDTO.titulo()))
                .findFirst().orElse(null);
        if (optLibro != null) {
            existe = true;
        }
        return existe;
    }

    private Autor comprobarAutor(int idAutor) {
        Autor autor = null;
        Optional<Autor> optAutor = autorRepository.findById(idAutor);
        if (optAutor.isPresent()) {
            autor = optAutor.get();
        }
        return autor;
    }

    private Genero comprobarGenero(int idGenero) {
        Genero genero = null;
        Optional<Genero> optGenero = generoRepository.findById(idGenero);
        if (optGenero.isPresent()) {
            genero = optGenero.get();
        }
        return genero;
    }
}
