package com.integrador.gym.Exception;

public class EmailYaRegistrado extends RuntimeException {
    public EmailYaRegistrado(String email) {
        super("EL email ya esta registrado: " + email);
    }
}
