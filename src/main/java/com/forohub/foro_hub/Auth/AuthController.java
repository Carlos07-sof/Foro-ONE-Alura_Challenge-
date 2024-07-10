package com.forohub.foro_hub.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forohub.foro_hub.Jwt.JwtService;
import com.forohub.foro_hub.User.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreoElectronico(), request.getContrasena()));
        UserDetails usuario = userRepository.findBycorreoElectronico(request.getCorreoElectronico()).orElseThrow();
        
        String token = jwtService.getToken(usuario);

        return AuthResponse.builder().token(token).build();
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        
        return ResponseEntity.ok(authService.register(request));
    }
    

}
