package com.integrador.gym.Factory;

import com.integrador.gym.Dto.Creacion.PagoCreacionDTO;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;
import org.springframework.stereotype.Component;


@Component
public interface PagoFactory {
    PagoModel crearDesdeDTO(PagoCreacionDTO dto, MembresiaModel membresia);
}