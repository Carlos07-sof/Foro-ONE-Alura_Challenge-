package com.forohub.foro_hub.Cursos;

import java.util.List;

public interface CursosService {
    List<Cursos> findAll();
    Cursos save (Cursos cursos);
    Cursos findByCategoria(String categoria);
    
} 
