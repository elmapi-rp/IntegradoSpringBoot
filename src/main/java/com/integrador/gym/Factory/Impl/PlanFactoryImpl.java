package com.integrador.gym.Factory.Impl;

import com.integrador.gym.Dto.Creacion.PlanCreacionDTO;
import com.integrador.gym.Factory.PlanFactory;
import com.integrador.gym.Model.PlanModel;
import org.springframework.stereotype.Component;

@Component
public class PlanFactoryImpl implements PlanFactory {
    @Override
    public PlanModel crearDesdeDTO(PlanCreacionDTO dto) {
        return PlanModel.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .duracionDias(dto.getDuracionDias())
                .beneficios(dto.getBeneficios())
                .estado(dto.getEstado())
                .build();
    }
}
