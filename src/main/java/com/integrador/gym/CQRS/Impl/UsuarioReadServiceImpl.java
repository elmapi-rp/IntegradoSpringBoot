package com.integrador.gym.CQRS.Impl;

import com.integrador.gym.CQRS.UsuarioReadService;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UsuarioReadServiceImpl implements UsuarioReadService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }
}
