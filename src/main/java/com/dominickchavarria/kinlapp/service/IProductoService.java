package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(int codigoProducto);

    List<Producto> buscarActivos();

    Producto actualizar(int codigoProducto, Producto producto);

    void eliminar(int codigoProducto);

    boolean existePorId(int codigoProducto);
}