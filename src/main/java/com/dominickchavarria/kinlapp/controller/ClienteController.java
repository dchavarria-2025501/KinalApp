package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Cliente;
import com.dominickchavarria.kinlapp.repository.ClienteRepository;
import com.dominickchavarria.kinlapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
//@RestController = @Controller + @RequestBody
@RequestMapping("/api/clientes")
//Todas las rutas de este controlador deben de empezar por /clientes
public class ClienteController {
    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IClienteService clienteService;
    //Como buena practica la Inyeccion de dependencias deben hacerse por el constructor
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Responde peticiones GET
    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(clientes);
        // 200 OK con la lista de clientes
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi){
        //@PathVariable Toma el valor de la URL y lo asigna al dpi.
        return clienteService.buscarPorDPI(dpi)
                //Si Optional tiene valor, devuelve 200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //{activos} es una variable de ruta(valor a buscar)
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> buscarPorActivos(){
        List<Cliente> clientesActivos = clienteService.buscarPorActivos();
        if (clientesActivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientesActivos);
    }

    //POST crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo generico" puede ser un Cliente o un String
        try{
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente pero puede lanzar una Excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 CREATED(mucha mas especifico mucho mas que el 200 para la creacion de un cliente)
        }catch(IllegalArgumentException e){
            //si hay error de validacion
            // 400 BAD REQUEST con el mensaje de error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi){
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try{
            if (!clienteService.existePorDpi(dpi)){
                return ResponseEntity.notFound().build();
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (Se ejecuto correctamente y no devuelve cuerpo)
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Actualizar cliente a traves del DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if(!clienteService.existePorDpi(dpi)){
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            //Actualizamos el cliente pero esto puede lanzar una excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 OK con el cliente ya actualizado
        }catch(IllegalArgumentException e){
            //Error cuando los datos sean incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(RuntimeException e){
            //Posiblemente cualquier otro error como: No Encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

}
