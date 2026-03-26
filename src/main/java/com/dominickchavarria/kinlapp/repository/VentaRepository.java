package com.dominickchavarria.kinlapp.repository;

import com.dominickchavarria.kinlapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstado(Long estado);
}