package com.integrador.gym.Exception;

public class MembresiaNoEncontrada extends RuntimeException {
    public MembresiaNoEncontrada(Long id) {
        super("Membresia no encontrada con ID" + id);
    }
}
