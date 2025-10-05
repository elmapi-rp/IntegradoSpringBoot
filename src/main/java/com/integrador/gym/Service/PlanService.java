package com.integrador.gym.Service;

import com.integrador.gym.Model.PlanModel;

import java.util.List;
import java.util.Optional;

public interface    PlanService {
    List<PlanModel> listarTodos();
    List<PlanModel> listarActivos();
    Optional<PlanModel> obtenerPorId(Long id);
    PlanModel crear(PlanModel plan);
    PlanModel actualizar(Long id, PlanModel planActualizado);
    void eliminar(Long id);
}
