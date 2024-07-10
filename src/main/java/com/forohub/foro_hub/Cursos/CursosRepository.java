package com.forohub.foro_hub.Cursos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CursosRepository extends JpaRepository<Cursos, Long>{
    Optional<Cursos> findByCategoria(String categoria);

}
