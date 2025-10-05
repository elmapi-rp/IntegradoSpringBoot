package com.integrador.gym.Factory.Impl;

import com.integrador.gym.Factory.MembresiaFactory;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PlanModel;
import com.integrador.gym.Model.UsuarioModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MembresiaFactoryImpl implements MembresiaFactory {
    @Override
    public MembresiaModel crearMembresia(ClienteModel cliente, PlanModel plan,
                                         UsuarioModel usuarioCreador, LocalDate fechaInicio) {
        if (cliente == null || plan == null || usuarioCreador == null) {
            throw new IllegalArgumentException("Cliente, plan y usuario creador son obligatorios");
        }

        LocalDate inicio = (fechaInicio != null) ? fechaInicio : LocalDate.now();
        LocalDate fin = inicio.plusDays(plan.getDuracionDias());

        return MembresiaModel.builder()
                .cliente(cliente)
                .plan(plan)
                .usuario(usuarioCreador)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .estadoMembresia(EstadoMembresia.ACTIVA)
                .build();
    }
}
