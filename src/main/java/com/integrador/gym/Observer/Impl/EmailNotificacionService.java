package com.integrador.gym.Observer.Impl;

import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Observer.MembresiaVencimiento;
import com.integrador.gym.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificacionService implements MembresiaVencimiento {
    @Autowired
    private EmailService emailService;
    @Override
    public void onMembresiaPorVencer(MembresiaModel membresia) {
        String to = membresia.getCliente().getUsuario().getEmail(); // ← Usa el email del usuario
        String subject = "Alerta: Tu membresía vence pronto";
        String body = "Hola " + membresia.getCliente().getNombre() + ", tu membresía " +
                membresia.getPlan().getNombre() + " vence el " +
                membresia.getFechaFin() + ". ¡No pierdas tu acceso!";

        emailService.enviarEmail(to, subject, body);
    }
}
