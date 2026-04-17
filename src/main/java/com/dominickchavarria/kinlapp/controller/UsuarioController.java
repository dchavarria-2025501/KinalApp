package com.dominickchavarria.kinlapp.controller;

import com.dominickchavarria.kinlapp.entity.Usuario;
import com.dominickchavarria.kinlapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController{
    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorId(
            @PathVariable Long codigoUsuario){
        return usuarioService
                .buscarPorId(codigoUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> buscarActivos(){
        List<Usuario> usuarios = usuarioService.buscarActivos();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> guardar(
            @RequestBody Usuario usuario){
        try{
            Usuario nuevo = usuarioService.guardar(usuario);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoUsuario, @RequestBody Usuario usuario){
        if (!usuarioService.existePorId(codigoUsuario)){
            return ResponseEntity.notFound().build();
        }
        Usuario actualizado = usuarioService.actualizar(codigoUsuario, usuario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoUsuario){
        if (!usuarioService.existePorId(codigoUsuario)){
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(codigoUsuario);
        return ResponseEntity.noContent().build();
    }
}