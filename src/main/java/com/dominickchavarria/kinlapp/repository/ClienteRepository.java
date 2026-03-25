package com.dominickchavarria.kinlapp.repository;

import com.dominickchavarria.kinlapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
}
