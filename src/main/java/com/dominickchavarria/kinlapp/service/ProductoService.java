package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Producto;
import com.dominickchavarria.kinlapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {

        validarProducto(producto);

        if (producto.getEstado() == 0) {
            producto.setEstado(1);
        }

        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(String idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarActivos() {
        return productoRepository.findByEstado(1);
    }

    @Override
    public Producto actualizar(String idProducto, Producto producto) {

        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException(
                    "El producto no se encontro con el ID " + idProducto);
        }

        producto.setIdProducto(idProducto);

        validarProducto(producto);

        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(String idProducto) {

        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException(
                    "El producto no se encontro");
        }

        productoRepository.deleteById(idProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(String idProducto) {
        return productoRepository.existsById(idProducto);
    }

    private void validarProducto(Producto producto) {

        if (producto.getIdProducto() == null ||
                producto.getIdProducto().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El ID del producto es obligatorio");
        }

        if (producto.getPrecio() == null ||
                producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El precio debe ser mayor a 0");
        }

        if (producto.getStock() < 0) {

            throw new IllegalArgumentException(
                    "El stock no puede ser negativo");
        }
    }
}