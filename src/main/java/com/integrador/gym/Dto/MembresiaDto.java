package com.integrador.gym.Dto;

import com.integrador.gym.Model.Enum.EstadoMembresia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
public class MembresiaDto {
    private Long idMembresia;
    private Long idCliente;
    private String nombreCliente;
    private Long idPlan;
    private String nombrePlan;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoMembresia estadoMembresia;
    private Long idUsuarioCreador;
    private String nombreUsuarioCreador;


}
