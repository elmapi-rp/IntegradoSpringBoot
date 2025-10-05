package com.integrador.gym.Exception;

public class ClienteDniDuplicado extends RuntimeException {
    public ClienteDniDuplicado(String dni) {
        super("Ya existe un cliente con DNI: " + dni);
    }
}
