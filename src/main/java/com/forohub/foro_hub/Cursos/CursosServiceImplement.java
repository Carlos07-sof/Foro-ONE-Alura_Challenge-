package com.forohub.foro_hub.Cursos;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CursosServiceImplement implements CursosService {
    
    private final CursosRepository cursosRepository;

    public CursosServiceImplement(CursosRepository cursosRepository) {
        this.cursosRepository = cursosRepository;
    }
    
    @Transactional
    @Override
    public List<Cursos> findAll() {
        List<Cursos> cursos = cursosRepository.findAll();
        if (cursos.isEmpty()) {
            throw new RuntimeException("No se encontraron cursos");
        }
        return cursos;
    }

    @Transactional
    @Override
    public Cursos save(Cursos cursos) {
        if (cursos.getNombre() == null || cursos.getNombre().isEmpty() && cursos.getCategoria() == null || cursos.getCategoria().isEmpty()) {
            throw new RuntimeException("Campos Vacios");
        }
        return cursosRepository.save(cursos);
    }

    @Transactional
    @Override
    public Cursos findByCategoria(String categoria) {
        return cursosRepository.findByCategoria(categoria).orElse(null);
    }

}
