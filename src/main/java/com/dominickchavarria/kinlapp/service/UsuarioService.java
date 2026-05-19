package com.dominickchavarria.kinlapp.service;

import com.dominickchavarria.kinlapp.entity.Usuario;
import com.dominickchavarria.kinlapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService, UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario){
        validarUsuario(usuario);
        if (usuario.getEstado() == null){
            usuario.setEstado(1L);
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long codigoUsuario){
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarActivos(){
        return usuarioRepository.findByEstado(1L);
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario){
        if (!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El usuario no se encontro con el codigo " + codigoUsuario);
        }
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long codigoUsuario){
        if (!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El usuario no se encontro");
        }
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (usuario.getEstado() == null || usuario.getEstado() != 1L) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        String role = "ROLE_" + usuario.getRol().toUpperCase();

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(new SimpleGrantedAuthority(role))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long codigoUsuario){
        return usuarioRepository.existsById(codigoUsuario);
    }

    private void validarUsuario(Usuario usuario){
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("El password es obligatorio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()){
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }
}