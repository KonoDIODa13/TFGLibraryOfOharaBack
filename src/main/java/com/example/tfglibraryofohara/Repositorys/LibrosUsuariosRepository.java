package com.example.tfglibraryofohara.Repositorys;

import com.example.tfglibraryofohara.Entities.LibrosUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrosUsuariosRepository extends JpaRepository<LibrosUsuarios, Integer> {

    Optional<LibrosUsuarios> findByUsuario_IdAndLibro_Id(Integer id_usuario, Integer id_libro);
}
