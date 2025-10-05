package com.integrador.gym.Service;

import com.integrador.gym.Model.PagoModel;

import java.util.List;

public interface PagoService {
    List<PagoModel> listarPorMembresia(Long idMembresia);
    PagoModel crear(PagoModel pago);
}
