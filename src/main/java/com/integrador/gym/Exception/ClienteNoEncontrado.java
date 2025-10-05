package com.integrador.gym.Exception;

public class ClienteNoEncontrado extends RuntimeException {
    public ClienteNoEncontrado(Long id) {
        super("Cliente no encontrado con ID: " + id);
    }
}
