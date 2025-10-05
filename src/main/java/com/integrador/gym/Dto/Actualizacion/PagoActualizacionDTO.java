package com.integrador.gym.Dto.Actualizacion;

import com.integrador.gym.Model.Enum.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class PagoActualizacionDTO {
    @NotNull(message = "ID de membresía es obligatorio")
    private Long idMembresia;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "Método de pago es obligatorio")
    private MetodoPago metodoPago;

    private LocalDateTime fechaPago;

    private String comprobante;
}
