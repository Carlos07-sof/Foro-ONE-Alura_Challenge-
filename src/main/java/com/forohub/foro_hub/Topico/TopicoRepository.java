package com.forohub.foro_hub.Topico;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
    Optional<Topico> findById(Long id);

}
