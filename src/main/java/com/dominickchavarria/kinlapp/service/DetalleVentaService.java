package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import com.dominickchavarria.kinlapp.entity.Producto;
import com.dominickchavarria.kinlapp.entity.Venta;
import com.dominickchavarria.kinlapp.repository.DetalleVentaRepository;
import com.dominickchavarria.kinlapp.repository.ProductoRepository;
import com.dominickchavarria.kinlapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository,
                               VentaRepository ventaRepository,
                               ProductoRepository productoRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos(){
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta){
        if(detalleVenta.getSubtotal() == null || detalleVenta.getSubtotal().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El subtotal no puede ser negativo");
        }
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorId(Long codigoDetalleVenta){
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> buscarPorVenta(Long codigoVenta){
        return detalleVentaRepository.findByVentaCodigoVenta(codigoVenta);
    }

    @Override
    public DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta){
        if(!detalleVentaRepository.existsById(codigoDetalleVenta)){
            throw new RuntimeException("El detalle de venta no existe");
        }
        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(Long codigoDetalleVenta){
        if(!detalleVentaRepository.existsById(codigoDetalleVenta)){
            throw new RuntimeException("El detalle de venta no existe");
        }
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    public boolean existePorId(Long codigoDetalleVenta){
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
}