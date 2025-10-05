package com.integrador.gym.Factory;

import com.integrador.gym.Dto.Creacion.PlanCreacionDTO;
import com.integrador.gym.Model.PlanModel;

public interface PlanFactory {
    PlanModel crearDesdeDTO(PlanCreacionDTO dto);
}
