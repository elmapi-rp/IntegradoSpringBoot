package com.integrador.gym.Dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class RegistrarClienteDTO {

    @NotBlank(message = "DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "DNI debe tener 7 u 8 dígitos")
    private String dni;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido es obligatorio")
    private String apellido;

    @Pattern(regexp = "\\d{6,15}", message = "Teléfono debe contener entre 6 y 15 dígitos")
    private String telefono;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "ID del usuario creador es obligatorio")
    private Long idUsuarioCreador;

    @NotNull(message = "ID del plan es obligatorio")
    private Long idPlan;

    @NotNull(message = "Método de pago es obligatorio")
    private String metodoPago;

    @NotNull(message = "Monto del pago es obligatorio")
    private BigDecimal monto;

    @NotBlank(message = "Email obligatorio")
   private String email;
}