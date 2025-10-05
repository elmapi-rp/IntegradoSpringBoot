package com.integrador.gym.Exception;

public class PagoNoEncontrado extends RuntimeException {
    public PagoNoEncontrado(Long id) {
        super("Pago no encontrado con ID: " + id);
    }
}
