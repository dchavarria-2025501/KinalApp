package com.dominickchavarria.kinlapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Venta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Long codigoVenta;
    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;
    @Column
    private BigDecimal total;
    @Column
    private Long estado;

    // RELACION con Cliente
    @ManyToOne
    @JoinColumn(name = "Clientes_dpi_cliente")
    private Cliente cliente;

    // RELACION con Usuario
    @ManyToOne
    @JoinColumn(name = "Usuarios_codigo_usuario")
    private Usuario usuario;

    public Venta(){
    }

    public Venta(Long codigoVenta, LocalDate fechaVenta, BigDecimal total, Long estado, Cliente cliente, Usuario usuario){
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public Long getCodigoVenta(){
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta){
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta(){
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta){
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public void setTotal(BigDecimal total){
        this.total = total;
    }

    public Long getEstado(){
        return estado;
    }

    public void setEstado(Long estado){
        this.estado = estado;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}