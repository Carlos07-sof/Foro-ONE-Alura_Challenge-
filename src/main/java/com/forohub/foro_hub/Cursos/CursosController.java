package com.forohub.foro_hub.Cursos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class CursosController {
    private final CursosService cursosService;
    
    @GetMapping(value = "cursos")
    public ResponseEntity <Object> getCursos() throws RuntimeException {
        List<Cursos> list = cursosService.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "cursos")    
    public ResponseEntity <Object> create(@RequestBody Cursos cursos) 
    {
       Map<String, Object> response = new HashMap<>();
        try {
            Cursos newCurso = cursosService.save(cursos);
            return new ResponseEntity<>(newCurso, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
