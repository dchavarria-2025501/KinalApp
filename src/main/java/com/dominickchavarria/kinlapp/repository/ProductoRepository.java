package com.dominickchavarria.kinlapp.repository;

import com.dominickchavarria.kinlapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,String> {
    List<Producto> findByEstado(int estado);
}