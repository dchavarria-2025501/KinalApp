package com.dominickchavarria.kinlapp.controller;

//ProductoViewController
import com.dominickchavarria.kinlapp.entity.Producto;
import com.dominickchavarria.kinlapp.service.IProductoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoViewController {

    private final IProductoService productoService;

    public ProductoViewController(IProductoService productoService) {
        this.productoService = productoService;
    }

    //GetMapping - Listar
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{codigoProducto}")
    public String editar(@PathVariable Long codigoProducto, Model model) {

        productoService.buscarPorId(codigoProducto)
                .ifPresent(producto ->
                        model.addAttribute("producto", producto));

        return "producto-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{codigoProducto}")
    public String eliminar(@PathVariable Long codigoProducto) {

        productoService.eliminar(codigoProducto);

        return "redirect:/productos";
    }
}