package com.integrador.gym.Observer.Impl;

import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Observer.MembresiaVencimiento;
import org.springframework.stereotype.Service;

import java.util.Observer;

@Service
public class SMSNotificacion implements MembresiaVencimiento {

    @Override
    public void onMembresiaPorVencer(MembresiaModel membresia) {
        System.out.println("Enviando SMS a " + membresia.getCliente().getTelefono() + " que su membresía vence pronto.");

    }
}
