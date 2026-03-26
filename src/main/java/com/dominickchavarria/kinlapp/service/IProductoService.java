package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long codigoProducto);

    List<Producto> buscarActivos();

    Producto actualizar(Long codigoProducto, Producto producto);

    void eliminar(Long codigoProducto);

    boolean existePorId(Long codigoProducto);
}