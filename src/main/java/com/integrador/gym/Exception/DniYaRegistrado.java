package com.integrador.gym.Exception;

public class DniYaRegistrado extends RuntimeException {
    public DniYaRegistrado(String dni) {
        super("El dni ya esta registrado: " + dni);
    }
}
