package com.integrador.gym.Model;

import com.integrador.gym.Model.Enum.MetodoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_membresia", nullable = false)
    private MembresiaModel membresia;

    @NotNull(message = "El monto es obligatorio")
    @Column(precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    private String comprobante;

    @PrePersist
    public void onCreate() {
        if (fechaPago == null) {
            fechaPago = LocalDateTime.now();
        }
    }
}
