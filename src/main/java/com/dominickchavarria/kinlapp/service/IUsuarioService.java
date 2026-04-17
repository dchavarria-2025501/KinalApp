package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService{

    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long codigoUsuario);

    List<Usuario> buscarActivos();

    Usuario actualizar(Long codigoUsuario, Usuario usuario);

    void eliminar(Long codigoUsuario);

    boolean existePorId(Long codigoUsuario);

    Optional<Usuario> buscarPorUsername(String username);
}