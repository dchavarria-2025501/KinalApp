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
        if (producto.getEstado() == null) {
            producto.setEstado(1L);
        }
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(Long codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarActivos() {
        return productoRepository.findByEstado(1L);
    }

    @Override
    public Producto actualizar(
            Long codigoProducto,
            Producto producto) {
        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException(
                    "El producto no se encontro con el codigo " + codigoProducto);
        }
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long codigoProducto) {
        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException(
                    "El producto no se encontro");
        }
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombreProducto() == null ||
                producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() == null ||
                producto.getPrecio()
                        .compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor a 0");
        }

        if (producto.getStock() == null ||
                producto.getStock() < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo");
        }
    }
}