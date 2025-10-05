package com.integrador.gym.Service;

import com.integrador.gym.Model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioModel> listarTodos();

    Optional<UsuarioModel> obtenerPorId(Long id);

    UsuarioModel crear(UsuarioModel usuario);

    UsuarioModel actualizar(Long id, UsuarioModel usuarioActualizado);

    void eliminar(Long id);

}
