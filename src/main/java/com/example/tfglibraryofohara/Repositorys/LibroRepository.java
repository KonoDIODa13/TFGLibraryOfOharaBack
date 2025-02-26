package com.example.tfglibraryofohara.Repositorys;

import com.example.tfglibraryofohara.Entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
}
