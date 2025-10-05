package com.integrador.gym.Dto;

import com.integrador.gym.Model.Enum.EstadoPlan;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class PlanDTO {
    private Long idPlan;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionDias;
    private String beneficios;
    private EstadoPlan estado;
    private LocalDateTime fechaCreacion;
}
