package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Cliente;
import com.dominickchavarria.kinlapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService{
    List<Venta> listarTodos();

    Venta guardar(Venta venta);

    Optional<Venta> buscarPorId(Long codigoVenta);

    List<Venta> buscarActivos();

    Venta actualizar(Long codigoVenta, Venta venta);

    void eliminar(Long codigoVenta);

    boolean existePorId(Long codigoVenta);

    List<Cliente> listarClientes();
}
