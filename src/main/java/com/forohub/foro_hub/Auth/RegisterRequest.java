package com.forohub.foro_hub.Auth;


import com.forohub.foro_hub.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    String nombre;
    String correoElectronico;
    String contrasena;
    Role role;
}
