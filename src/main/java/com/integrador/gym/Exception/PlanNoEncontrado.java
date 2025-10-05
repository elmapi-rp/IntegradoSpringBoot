package com.integrador.gym.Exception;

public class PlanNoEncontrado extends RuntimeException {
    public PlanNoEncontrado(Long id) {
        super("Plan no encontrado con ID"+ id);
    }
}
