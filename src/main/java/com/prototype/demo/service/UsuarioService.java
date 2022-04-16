package com.prototype.demo.service;

import com.prototype.demo.model.Rol;
import com.prototype.demo.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {
    Usuario saveUsuario(Usuario user);
    Rol saveRol(Rol role);
    void addRolToUsuario(String username, String roleName);
    Usuario getUsuario(String username);
    List<Usuario> getUsuarios();
}