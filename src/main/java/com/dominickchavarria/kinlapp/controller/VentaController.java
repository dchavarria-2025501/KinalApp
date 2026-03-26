package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Venta;
import com.dominickchavarria.kinlapp.service.IVentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        return ResponseEntity.ok(ventaService.listarTodos());
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Long codigoVenta){
        return ventaService
                .buscarPorId(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venta> guardar(@RequestBody Venta venta){
        return ResponseEntity.ok(ventaService.guardar(venta));
    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long codigoVenta, @RequestBody Venta venta){
        return ResponseEntity.ok(ventaService.actualizar(codigoVenta, venta));
    }
    
    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoVenta){
        ventaService.eliminar(codigoVenta);
        return ResponseEntity.noContent().build();
    }
}
