package com.forohub.foro_hub.User;

import jakarta.persistence.*;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="usuario", uniqueConstraints ={@UniqueConstraint(columnNames = {"correoElectronico"})})
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;
    private String nombre;
    @Column(nullable = false)
    private String correoElectronico;
    @JsonIgnore
    private String contrasena;
    @Enumerated(EnumType.STRING)
    Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "perfil_usuario",
        joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id_perfil")
    )

    private List<Perfil> perfilList;

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId_user() {
        return id_user;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public Role getRole() {
        return role;
    }

}
