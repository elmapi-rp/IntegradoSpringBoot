package com.integrador.gym.Exception;

public class RolNoPermitido extends RuntimeException {
    public RolNoPermitido(String rol) {
        super("Rol no permitido: " + rol);
    }
}
