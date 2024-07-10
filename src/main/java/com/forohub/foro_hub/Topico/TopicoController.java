package com.forohub.foro_hub.Topico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forohub.foro_hub.Cursos.Cursos;
import com.forohub.foro_hub.Cursos.CursosRepository;
import com.forohub.foro_hub.User.Usuario;
import com.forohub.foro_hub.User.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class TopicoController {

    private final TopicoService topicoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursosRepository cursoRepository;
    
    @GetMapping(value = "topico")
    public ResponseEntity <Object> getCursos() throws RuntimeException {
        List<Topico> list = topicoService.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value="topico")
    public ResponseEntity<Object> create(@RequestBody Map<String, Object> topico){
        try {
            Long usuarioId = Long.parseLong(topico.get("usuarioId").toString());
            Long cursoId = Long.parseLong(topico.get("cursoId").toString());

            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
            Optional<Cursos> cursoOptional = cursoRepository.findById(cursoId);

            if (!usuarioOptional.isPresent() || !cursoOptional.isPresent()) {
                throw new EntityNotFoundException("Usuario o curso no encontrado");
            }

            Usuario usuario = usuarioOptional.get();
            Cursos curso = cursoOptional.get();

            Usuario usuarioToDisplay = new Usuario();
            usuarioToDisplay.setId_user(usuario.getId_user());
            usuarioToDisplay.setNombre(usuario.getNombre());
            usuarioToDisplay.setCorreoElectronico(usuario.getCorreoElectronico());
            usuarioToDisplay.setRole(usuario.getRole());
            
            // Aquí construyes tu objeto Topico usando los datos recibidos
            Topico newTopico = new Topico();
            newTopico.setTitulo(topico.get("titulo").toString());
            newTopico.setMensaje(topico.get("mensaje").toString());
            newTopico.setRespuestas(topico.get("respuestas").toString());
            newTopico.setUsuario(usuario);
            newTopico.setCurso(curso);

            // Guardar el nuevo Topico
            Topico savedTopico = topicoService.save(newTopico);

            return new ResponseEntity<>(savedTopico, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error en los tipos de datos de entrada");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="topico/{id}")
    public ResponseEntity<Object> updateTopico(@PathVariable Long id, @RequestBody Map<String, Object> topico) {
        try {
            Optional<Topico> topicoOptional = topicoService.findById(id);
            
            if (!topicoOptional.isPresent()) {
                throw new EntityNotFoundException("Topico no encontrado");
            }
            
            Topico topicos = topicoOptional.get();
            
            // Actualizar el mensaje si está presente en el cuerpo de la solicitud
            if (topico.containsKey("mensaje")) {
                topicos.setMensaje(topico.get("mensaje").toString());
            }

            // Actualizar el titulo si está presente en el cuerpo de la solicitud
            if (topico.containsKey("titulo")) {
                topicos.setTitulo(topico.get("titulo").toString());
            }

            // Actualizar las respuestas si están presentes en el cuerpo de la solicitud
            if (topico.containsKey("respuestas")) {
                topicos.setRespuestas(topico.get("respuestas").toString());
            }
            
            // Guardar el topico actualizado
            Topico savedTopico = topicoService.save(topicos);
            
            return new ResponseEntity<>(savedTopico, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
    }
    @DeleteMapping(value="topico/{id}")
    public ResponseEntity<Object> deleteTopico(@PathVariable Long id) {
        try {
            Optional<Topico> topicoOptional = topicoService.findById(id);
            
            if (!topicoOptional.isPresent()) {
                throw new EntityNotFoundException("Topico no encontrado");
            }
            
            Topico topico = topicoOptional.get();

            topico.setStatus(false);
            
            topicoService.save(topico);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Topico eliminado correctamente");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
