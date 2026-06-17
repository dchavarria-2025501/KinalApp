package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Venta;
import com.dominickchavarria.kinlapp.service.ClienteService;
import com.dominickchavarria.kinlapp.service.IVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaViewController {

    private final IVentaService ventaService;

    public VentaViewController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        return "ventas";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", ventaService.listarClientes());
        model.addAttribute("usuarios", ventaService.listarUsuarios());
        return "venta-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("venta") Venta venta) {
        ventaService.guardar(venta);
        return "redirect:/ventas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{codigoVenta}")
    public String editar(@PathVariable Long codigoVenta, Model model) {

        ventaService.buscarPorId(codigoVenta)
                .ifPresent(venta ->
                        model.addAttribute("venta", venta));

        model.addAttribute("clientes", ventaService.listarClientes());
        model.addAttribute("usuarios", ventaService.listarUsuarios());

        return "venta-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{codigoVenta}")
    public String eliminar(@PathVariable Long codigoVenta) {

        ventaService.eliminar(codigoVenta);

        return "redirect:/ventas";
    }
}
