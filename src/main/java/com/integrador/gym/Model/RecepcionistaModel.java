package com.integrador.gym.Model;

import jakarta.persistence.*;

import lombok.*;

@Entity @Table(name = "recepcionista")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class RecepcionistaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;

    private String horarioTrabajo;
    private String sucursal;
    private String codigoEmpleado;
}
