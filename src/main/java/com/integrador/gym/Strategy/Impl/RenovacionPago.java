package com.integrador.gym.Strategy.Impl;

import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;
import com.integrador.gym.Strategy.ValidacionPago;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RenovacionPago implements ValidacionPago {
    @Override
    public void validar(PagoModel pago, MembresiaModel membresia) {
        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0.");
        }
    }
}
