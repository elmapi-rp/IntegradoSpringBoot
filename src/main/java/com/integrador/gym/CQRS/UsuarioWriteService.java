package com.integrador.gym.CQRS;

import com.integrador.gym.Dto.Actualizacion.UsuarioActualizacionDTO;
import com.integrador.gym.Dto.Creacion.UsuarioCreacionDTO;
import com.integrador.gym.Dto.UsuarioDTO;

public interface UsuarioWriteService {
    UsuarioDTO crear(UsuarioCreacionDTO dto);
    UsuarioDTO actualizar(Long id, UsuarioActualizacionDTO dto);

}
