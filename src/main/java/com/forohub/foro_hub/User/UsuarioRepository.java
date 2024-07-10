package com.forohub.foro_hub.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id_user);
    Optional<Usuario> findBycorreoElectronico(String correoElectronico);

}
