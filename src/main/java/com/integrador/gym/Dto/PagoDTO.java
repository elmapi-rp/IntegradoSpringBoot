package com.integrador.gym.Dto;

import com.integrador.gym.Model.Enum.MetodoPago;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class PagoDTO {
    private Long idPago;
    private Long idMembresia;
    private String nombreMembresia;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private LocalDateTime fechaPago;
    private String comprobante;
}
