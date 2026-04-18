package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Venta;
import com.dominickchavarria.kinlapp.service.IVentaService;
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
    public String nuevo(Model model) {model.addAttribute("venta", new Venta());
        model.addAttribute(
                "clientes",
                ventaService.listarClientes()
        );
        return "venta-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("venta") Venta venta) {
        ventaService.guardar(venta);
        return "redirect:/ventas";
    }
}
