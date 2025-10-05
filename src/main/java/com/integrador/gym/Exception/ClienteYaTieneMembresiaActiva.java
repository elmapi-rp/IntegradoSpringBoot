package com.integrador.gym.Exception;

public class ClienteYaTieneMembresiaActiva extends RuntimeException {
    public ClienteYaTieneMembresiaActiva(Long IdCliente) {
        super("El Cliente con ID"+ IdCliente +"ya tiene membresía activa");
    }
}
