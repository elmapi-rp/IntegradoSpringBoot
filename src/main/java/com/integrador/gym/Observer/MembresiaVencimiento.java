package com.integrador.gym.Observer;

import com.integrador.gym.Model.MembresiaModel;

public interface MembresiaVencimiento {
    void onMembresiaPorVencer(MembresiaModel membresia);
}
