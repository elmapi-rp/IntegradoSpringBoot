package com.integrador.gym.Service;

import com.integrador.gym.Model.MembresiaModel;

import java.util.List;
import java.util.Optional;

public interface MembresiaService {
    List<MembresiaModel> listarTodas();
    Optional<MembresiaModel> obtenerPorId(Long id);
    MembresiaModel crear(MembresiaModel membresia);
    MembresiaModel actualizar(Long id, MembresiaModel membresiaActualizada);
    void cancelar(Long id);
}
