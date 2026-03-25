package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoService {

    //Lista todos los productos
    List<Producto> listarTodos();

    //Guarda los productos
    Producto guardar(Producto producto);

    //Busca por id
    Optional<Producto> buscarPorId(String idProducto);

    //Lista los productos activos
    List<Producto> buscarActivos();

    //Actualiza los productos necesarios
    Producto actualizar(String idProducto, Producto producto);

    //Elimina algun producto
    void eliminar(String idProducto);

    //Verifica si existe
    boolean existePorId(String idProducto);
}