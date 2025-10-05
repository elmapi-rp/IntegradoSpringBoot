package com.integrador.gym.Service.Impl;

import com.integrador.gym.Exception.*;
import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PagoModel;
import com.integrador.gym.Repository.PagoRepository;
import com.integrador.gym.Service.MembresiaService;
import com.integrador.gym.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MembresiaService membresiaService;


    @Override
    public List<PagoModel> listarPorMembresia(Long idMembresia) {
        return pagoRepository.findByMembresiaIdMembresia(idMembresia);
    }


    @Override
    public PagoModel crear(PagoModel pago) {
        // Validar membresía
        MembresiaModel membresia = validarMembresia(pago.getMembresia().getIdMembresia());

        // Validar fecha de pago (no futura)
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        }
        if (pago.getFechaPago().isAfter(LocalDateTime.now().plusMinutes(5))) {
            throw new PagoInvalido("La fecha de pago no puede ser futura.");
        }

        // Validar monto: debe coincidir con el plan (solo primera cuota por ahora)
        long cantidadPagos = pagoRepository.countByMembresiaIdMembresia(membresia.getIdMembresia());
        if (cantidadPagos == 0) {
            BigDecimal montoEsperado = membresia.getPlan().getPrecio();
            if (pago.getMonto() == null || pago.getMonto().compareTo(montoEsperado) != 0) {
                throw new PagoInvalido(
                        "El monto del primer pago debe ser exactamente: " + montoEsperado
                );
            }
        }
        // En futuras versiones: permitir pagos parciales o renovaciones

        if (membresia.getEstadoMembresia() == EstadoMembresia.CANCELADA) {
            throw new PagoInvalido("No se puede pagar una membresía cancelada.");
        }

        pago.setMembresia(membresia);

        return pagoRepository.save(pago);
    }


    private MembresiaModel validarMembresia(Long idMembresia) {
        return membresiaService.obtenerPorId(idMembresia)
                .orElseThrow(() -> new PagoInvalido("Membresía no encontrada con ID: " + idMembresia));
    }
}
