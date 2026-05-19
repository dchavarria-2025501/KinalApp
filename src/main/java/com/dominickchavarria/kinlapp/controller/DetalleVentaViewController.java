package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import com.dominickchavarria.kinlapp.service.IDetalleVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detalle_ventas")
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
        model.addAttribute("ventas", detalleVentaService.listarVentas());
        model.addAttribute("productos", detalleVentaService.listarProductos());
        return "detalleVenta-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("detalleVenta") DetalleVenta detalleVenta) {
        detalleVentaService.guardar(detalleVenta);
        return "redirect:/detalle_ventas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{codigoDetalleVenta}")
    public String editar(@PathVariable Long codigoDetalleVenta, Model model) {

        detalleVentaService.buscarPorId(codigoDetalleVenta)
                .ifPresent(detalleVenta ->
                        model.addAttribute("detalleVenta", detalleVenta));

        model.addAttribute("ventas", detalleVentaService.listarVentas());
        model.addAttribute("productos", detalleVentaService.listarProductos());

        return "detalleVenta-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{codigoDetalleVenta}")
    public String eliminar(@PathVariable Long codigoDetalleVenta) {

        detalleVentaService.eliminar(codigoDetalleVenta);

        return "redirect:/detalle_ventas";
    }
}