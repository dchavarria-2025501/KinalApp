package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Usuario;
import com.dominickchavarria.kinlapp.service.IUsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final IUsuarioService usuarioService;

    public UsuarioViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{codigoUsuario}")
    public String editar(@PathVariable Long codigoUsuario, Model model) {

        usuarioService.buscarPorId(codigoUsuario)
                .ifPresent(usuario ->
                        model.addAttribute("usuario", usuario));

        return "usuario-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{codigoUsuario}")
    public String eliminar(@PathVariable Long codigoUsuario) {

        usuarioService.eliminar(codigoUsuario);

        return "redirect:/usuarios";
    }
}
