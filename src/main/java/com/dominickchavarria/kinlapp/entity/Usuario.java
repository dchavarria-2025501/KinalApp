package com.dominickchavarria.kinlapp.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Long codigoUsuario;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String rol;
    @Column(nullable = false)
    private Long estado;

    public Usuario(){
    }

    public Usuario(Long codigoUsuario, String username, String password, String email, String rol, Long estado){
        this.codigoUsuario = codigoUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getCodigoUsuario(){
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario){
        this.codigoUsuario = codigoUsuario;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    public Long getEstado(){
        return estado;
    }

    public void setEstado(Long estado){
        this.estado = estado;
    }
}