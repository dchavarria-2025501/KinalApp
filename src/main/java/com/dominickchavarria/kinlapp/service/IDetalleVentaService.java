package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    List<DetalleVenta> listarTodos();

    DetalleVenta guardar(DetalleVenta detalleVenta);

    Optional<DetalleVenta> buscarPorId(Long codigoDetalleVenta);

    List<DetalleVenta> buscarPorVenta(Long codigoVenta);

    DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta);

    void eliminar(Long codigoDetalleVenta);

    boolean existePorId(Long codigoDetalleVenta);
}
