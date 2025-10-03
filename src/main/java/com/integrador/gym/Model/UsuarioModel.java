package com.integrador.gym.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String edad;
    private String email;
    private String password;
}
