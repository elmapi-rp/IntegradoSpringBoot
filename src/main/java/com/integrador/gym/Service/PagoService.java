package com.integrador.gym.Service;

import com.integrador.gym.Dto.Actualizacion.PagoActualizacionDTO;
import com.integrador.gym.Dto.Creacion.PagoCreacionDTO;
import com.integrador.gym.Dto.PagoDTO;
import com.integrador.gym.Model.PagoModel;

import java.util.List;

public interface PagoService {
    List<PagoModel> listarPorMembresia(Long idMembresia);
    PagoDTO crear(PagoCreacionDTO dto);
    PagoDTO actualizar(Long id, PagoActualizacionDTO dto);
    void eliminar(Long id);
}
