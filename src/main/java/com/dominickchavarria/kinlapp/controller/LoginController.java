package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Usuario;
import com.dominickchavarria.kinlapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model){
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getPassword().equals(password) && usuario.getEstado() == 1L) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "Contraseña incorrecta o usuario inactivo");
                return "login";
            }
        } else {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setEmail(username + "@kinlapp.com");
            nuevoUsuario.setRol("USER");
            nuevoUsuario.setEstado(1L);

            try {
                usuarioService.guardar(nuevoUsuario);
                model.addAttribute("mensaje", "¡Usuario creado exitosamente! Bienvenido/a " + username);
                return "redirect:/";
            } catch (Exception e) {
                model.addAttribute("error", "Error al crear usuario: " + e.getMessage());
                return "login";
            }
        }
    }
}