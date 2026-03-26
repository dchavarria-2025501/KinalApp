package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import com.dominickchavarria.kinlapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {
    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository){
        this.detalleVentaRepository = detalleVentaRepository;
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
        return  detalleVentaRepository.save(detalleVenta);
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
}
