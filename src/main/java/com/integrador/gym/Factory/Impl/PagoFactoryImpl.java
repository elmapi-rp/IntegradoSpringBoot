package com.integrador.gym.Factory.Impl;

import com.integrador.gym.Dto.Creacion.PagoCreacionDTO;
import com.integrador.gym.Factory.PagoFactory;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;
import org.springframework.stereotype.Component;

@Component
public class PagoFactoryImpl implements PagoFactory {

    @Override
    public PagoModel crearDesdeDTO(PagoCreacionDTO dto, MembresiaModel membresia) {
        return PagoModel.builder()
                .membresia(membresia)
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .fechaPago(dto.getFechaPago()) // puede ser null
                .comprobante(dto.getComprobante())
                .build();
    }
}
