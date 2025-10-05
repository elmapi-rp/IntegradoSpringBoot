package com.integrador.gym.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.integrador.gym.Model.Enum.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "DNI debe tener 7 u 8 dígitos")
    private String dni;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido es obligatorio")
    private String apellido;

    @Pattern(regexp = "\\d{6,15}", message = "Teléfono debe tener entre 6 y 15 dígitos")
    private String telefono;

    private String direccion;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Genero genero = Genero.NO_ESPECIFICADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioModel usuario;
}
