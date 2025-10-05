package com.integrador.gym.Factory;

import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PlanModel;
import com.integrador.gym.Model.UsuarioModel;

import java.time.LocalDate;

public interface MembresiaFactory {
    MembresiaModel crearMembresia(ClienteModel cliente, PlanModel plan,
                                  UsuarioModel usuarioCreador,
                                  LocalDate fechaInicio);
}
