package com.integrador.gym.Exception;

public class UsuarioNoAutorizado extends RuntimeException {
    public UsuarioNoAutorizado(String message) {
        super(message);
    }
}
