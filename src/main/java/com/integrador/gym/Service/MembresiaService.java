package com.integrador.gym.Service;

import com.integrador.gym.Dto.Creacion.MembresiaCreacionDTO;
import com.integrador.gym.Dto.MembresiaDto;
import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.MembresiaModel;

import java.util.List;
import java.util.Optional;

public interface MembresiaService {
    List<MembresiaModel> listarTodas();
    Optional<MembresiaModel> obtenerPorId(Long id);
    MembresiaModel crear(MembresiaModel membresia);
    void actualizar(Long id, EstadoMembresia nuevoEstado);
    void cancelar(Long id);
    MembresiaDto crear(MembresiaCreacionDTO dto);

}
