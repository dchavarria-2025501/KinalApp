package com.dominickchavarria.kinlapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;
    @Column(nullable = false)
    private Long cantidad;
    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    // RELACION con PRODUCTOS
    @ManyToOne(optional = false)
    @JoinColumn(name = "productos_codigo_producto", nullable = false)
    private Producto producto;

    // RELACION con VENTAS
    @ManyToOne(optional = false)
    @JoinColumn(name = "ventas_codigo_venta", nullable = false)
    private Venta venta;

    public DetalleVenta(){
    }

    public DetalleVenta(Long codigoDetalleVenta, Long cantidad, BigDecimal precioUnitario, BigDecimal subtotal, Producto producto, Venta venta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.venta = venta;
    }

    public Long getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
