package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Producto;
import com.dominickchavarria.kinlapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @RequestBody
@RequestMapping("/productos")
// Todas las rutas de este controlador empiezan con /productos
public class ProductoController{
    // Inyección del servicio
    //El controlador solo debe tener conexion con el Servicio
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService){
        this.productoService = productoService;
    }

    //Peticiones GET
    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
        //200 OK con la lista de productos
    }

    //{idProducto} variable de ruta(valor a buscar)
    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable String idProducto){
        //@PathVariable Toma el valor de la URL y lo asigna al idProducto
        return productoService.buscarPorId(idProducto)
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
        // 200 OK o 404 NOT FOUND
    }

    //{activos} es una variable de ruta(valor a buscar)
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> buscarActivos(){
        List<Producto> productosActivos = productoService.buscarActivos();
        if(productosActivos.isEmpty()){
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT
        }
        return ResponseEntity.ok(productosActivos);
        // 200 OK
    }

    // POST - crear un nuevo producto
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Producto
        //<?> significa "tipo generico" puede ser un Producto o un String
        try{
            Producto nuevoProducto = productoService.guardar(producto);
            //Intentamos guardar el producto pero puede lanzar una Excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
            // 201 CREATED
        }catch (IllegalArgumentException e){
            //si hay error de validacion
            // 400 BAD REQUEST
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE - eliminar producto
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable String idProducto){
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try{
            if(!productoService.existePorId(idProducto)){
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            productoService.eliminar(idProducto);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (Se ejecuto correctamente y no devuelve cuerpo)
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Actualizar producto a traves de idProducto
    @PutMapping("/{idProducto}")
    public ResponseEntity<?> actualizar(@PathVariable String idProducto, @RequestBody Producto producto){
        try{
            if(!productoService.existePorId(idProducto)){
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            //Actualizamos el producto pero esto puede lanzar una excepcion
            Producto productoActualizado = productoService.actualizar(idProducto, producto);
            return ResponseEntity.ok(productoActualizado);
            //200 OK con el cliente ya actualizado
        }catch (IllegalArgumentException e){
            //Error cuando los datos sean incorrectos
            //400 BAD REQUEST
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            //Posiblemente cualquier otro error como: No Encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
}