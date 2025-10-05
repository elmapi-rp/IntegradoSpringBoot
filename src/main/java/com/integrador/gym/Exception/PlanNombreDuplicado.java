package com.integrador.gym.Exception;

public class PlanNombreDuplicado extends RuntimeException {
    public PlanNombreDuplicado(String nombre) {
        super("Ya existe un plan con el nombre" + nombre);
    }
}
