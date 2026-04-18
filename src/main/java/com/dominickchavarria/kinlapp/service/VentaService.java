package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Cliente;
import com.dominickchavarria.kinlapp.entity.Venta;
import com.dominickchavarria.kinlapp.repository.ClienteRepository;
import com.dominickchavarria.kinlapp.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository){
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos(){
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta){
        if(venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El total no puede ser negativo");
        }
        if(venta.getEstado() == null){
            venta.setEstado(1L);
        }
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorId(Long codigoVenta){
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarActivos(){
        return ventaRepository.findByEstado(1L);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta){
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no existe");
        }
        venta.setCodigoVenta(codigoVenta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta){
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no existe");
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    public boolean existePorId(Long codigoVenta){
        return ventaRepository.existsById(codigoVenta);
    }

    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

}
