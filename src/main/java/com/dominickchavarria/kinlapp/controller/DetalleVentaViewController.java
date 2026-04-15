package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import com.dominickchavarria.kinlapp.service.IDetalleVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/detalleVentas")
public class DetalleVentaViewController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaViewController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalleVentas", detalleVentaService.listarTodos());
        return "detalleVentas";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("detalleVenta", new DetalleVenta());
        return "detalleVenta-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("detalleVenta") DetalleVenta detalleVenta) {
        detalleVentaService.guardar(detalleVenta);
        return "redirect:/vista/detalleVentas";
    }
}