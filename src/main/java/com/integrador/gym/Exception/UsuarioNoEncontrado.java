package com.integrador.gym.Exception;

public class UsuarioNoEncontrado extends RuntimeException {
    public UsuarioNoEncontrado(Long id) {
        super("usuario no encontrado: " + id);
    }
}
