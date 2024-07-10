package com.forohub.foro_hub.Auth;

import java.util.Collections;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forohub.foro_hub.Jwt.JwtService;
import com.forohub.foro_hub.User.Perfil;
import com.forohub.foro_hub.User.PerfilRepository;
import com.forohub.foro_hub.User.Usuario;
import com.forohub.foro_hub.User.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UsuarioRepository userRepository;

    private final PerfilRepository perfilRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreoElectronico(), request.getContrasena()));
        UserDetails user=userRepository.findBycorreoElectronico(request.getCorreoElectronico()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {

        Perfil perfil = Perfil.builder()
                .role(request.getRole())
                .build();
                
        perfilRepository.save(perfil);

        // Construir y guardar usuario
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correoElectronico(request.getCorreoElectronico())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .role(request.getRole())
                .perfilList(Collections.singletonList(perfil))
                .build();

        userRepository.save(usuario);
        

        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
    }
    
    public Optional<Usuario> buscarPorId(Long id_user) {
        return userRepository.findById(id_user);
    }

}
