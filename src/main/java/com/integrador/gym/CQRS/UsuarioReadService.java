package com.integrador.gym.CQRS;

import com.integrador.gym.Model.UsuarioModel;

import java.util.List;

public interface UsuarioReadService {
    List<UsuarioModel> listarTodos();
}
