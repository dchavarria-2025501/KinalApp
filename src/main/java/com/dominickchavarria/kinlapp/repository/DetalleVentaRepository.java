package com.dominickchavarria.kinlapp.repository;

import com.dominickchavarria.kinlapp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVentaCodigoVenta(Long codigoVenta);
}
