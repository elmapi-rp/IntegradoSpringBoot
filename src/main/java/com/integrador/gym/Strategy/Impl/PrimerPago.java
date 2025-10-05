package com.integrador.gym.Strategy.Impl;

import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;
import com.integrador.gym.Strategy.ValidacionPago;
import org.springframework.stereotype.Component;

@Component
public class PrimerPago implements ValidacionPago {
    @Override
    public void validar(PagoModel pago, MembresiaModel membresia) {
        if (pago.getMonto() == null || pago.getMonto().compareTo(membresia.getPlan().getPrecio()) != 0) {
            throw new RuntimeException("El monto del primer pago debe ser exactamente " + membresia.getPlan().getPrecio());
        }
    }
}
