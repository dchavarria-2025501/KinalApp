package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import com.dominickchavarria.kinlapp.service.IDetalleVentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle_ventas")
public class DetalleVentaController {
    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService){
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar(){
        return ResponseEntity.ok(detalleVentaService.listarTodos());
    }

    @GetMapping("/{codigoDetalleVenta}")
    public ResponseEntity<DetalleVenta> buscarPorId(@PathVariable Long codigoDetalleVenta){
        return detalleVentaService
                .buscarPorId(codigoDetalleVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> guardar(@RequestBody DetalleVenta detalleVenta){
        return ResponseEntity.ok(detalleVentaService.guardar(detalleVenta));
    }

    @DeleteMapping("/{codigoDetalleVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoDetalleVenta){
        detalleVentaService.eliminar(codigoDetalleVenta);
        return ResponseEntity.noContent().build();
    }
}
