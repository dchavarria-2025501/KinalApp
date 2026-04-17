package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Producto;
import com.dominickchavarria.kinlapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(
                productoService.listarTodos());
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long codigoProducto) {
        return productoService
                .buscarPorId(codigoProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> buscarActivos() {
        List<Producto> productos = productoService.buscarActivos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto) {
        try {
            Producto nuevo = productoService.guardar(producto);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{codigoProducto}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoProducto, @RequestBody Producto producto) {
        if (!productoService.existePorId(codigoProducto)){
            return ResponseEntity.notFound().build();
        }
        Producto actualizado = productoService.actualizar(codigoProducto, producto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoProducto) {
        if (!productoService.existePorId(codigoProducto)){
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(codigoProducto);
        return ResponseEntity.noContent().build();
    }
}