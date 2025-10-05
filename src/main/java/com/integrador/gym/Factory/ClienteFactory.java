package com.integrador.gym.Factory;

import com.integrador.gym.Dto.Creacion.ClienteCreacionDTO;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.UsuarioModel;

public interface ClienteFactory {
    ClienteModel crearDesdeDTO(ClienteCreacionDTO dto, UsuarioModel usuarioCreador);
}
