package com.forohub.foro_hub.Topico;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;


@Service
public class TopicoServiceImplement implements TopicoService{

    private final TopicoRepository topicoRepository;

    public TopicoServiceImplement(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;

    }
    
    @Transactional
    @Override
    public List<Topico> findAll() {
        List<Topico> topico = topicoRepository.findAll();   
        System.out.println(topico);
        if (topico.isEmpty()) {
            throw new RuntimeException("No se encontraron topicos");
        }
        return topico;
    }
    @Override
    public Topico save(Topico topico) {
        return topicoRepository.save(topico);
    }

    @Override
    public Optional<Topico> findById(Long id) {
        return topicoRepository.findById(id);
    }

}
