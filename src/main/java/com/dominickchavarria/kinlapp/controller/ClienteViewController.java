package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Cliente;
import com.dominickchavarria.kinlapp.service.IClienteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteViewController {

    private final IClienteService clienteService;

    public ClienteViewController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-form";
    }

    //metodo guardar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{dpi}")
    public String editar(@PathVariable String dpi, Model model) {
        clienteService.buscarPorDPI(dpi).ifPresent(c -> model.addAttribute("cliente", c));
        return "cliente-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi) {
        clienteService.eliminar(dpi);
        return "redirect:/clientes";
    }
}