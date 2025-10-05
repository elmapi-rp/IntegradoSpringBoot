package com.integrador.gym.Dto;

import com.integrador.gym.Model.Enum.Genero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
public class ClienteDTO {
    private Long idCliente;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private Long idUsuarioCreador;
    private String nombreUsuarioCreador;
}
