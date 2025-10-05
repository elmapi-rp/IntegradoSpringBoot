package com.integrador.gym.Strategy;

import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;

public interface ValidacionPago {
    void validar(PagoModel pago, MembresiaModel membresia);
}
