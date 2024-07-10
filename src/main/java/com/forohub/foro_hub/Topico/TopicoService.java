package com.forohub.foro_hub.Topico;

import java.util.List;
import java.util.Optional;


public interface TopicoService {

    List<Topico> findAll();
    Topico save (Topico topico);
    Optional<Topico> findById(Long id); 
}
