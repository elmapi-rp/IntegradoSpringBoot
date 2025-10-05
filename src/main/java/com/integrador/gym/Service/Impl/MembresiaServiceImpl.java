package com.integrador.gym.Service.Impl;

import com.integrador.gym.Exception.*;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.Enum.EstadoPlan;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PlanModel;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Repository.MembresiaRepository;
import com.integrador.gym.Service.ClienteService;
import com.integrador.gym.Service.MembresiaService;
import com.integrador.gym.Service.PlanService;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembresiaServiceImpl implements MembresiaService {

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PlanService planService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public List<MembresiaModel> listarTodas() {
        return membresiaRepository.findAll();
    }

    @Override
    public Optional<MembresiaModel> obtenerPorId(Long id) {
        return membresiaRepository.findById(id);
    }

    @Override
    public MembresiaModel crear(MembresiaModel membresia) {
        // Validar cliente
        ClienteModel cliente = validarCliente(membresia.getCliente().getIdCliente());

        // Validar plan
        PlanModel plan = validarPlan(membresia.getPlan().getIdPlan());

        // Validar usuario creador
        UsuarioModel usuarioCreador = validarUsuarioCreador(membresia.getUsuario().getIdUsuario());

        // Regla: No permitir más de una membresía ACTIVA por cliente
        if (membresiaRepository.existsByClienteIdClienteAndEstadoMembresia(
                cliente.getIdCliente(), EstadoMembresia.ACTIVA)) {
            throw new ClienteYaTieneMembresiaActiva(cliente.getIdCliente());
        }

        // Establecer fecha de inicio (si no se proporciona, usar hoy)
        if (membresia.getFechaInicio() == null) {
            membresia.setFechaInicio(LocalDate.now());
        }

        // Calcular fecha de fin
        LocalDate fechaFin = membresia.getFechaInicio().plusDays(plan.getDuracionDias());
        membresia.setFechaFin(fechaFin);

        // Establecer estado
        membresia.setEstadoMembresia(EstadoMembresia.ACTIVA);

        // Asociar objetos completos (no solo IDs)
        membresia.setCliente(cliente);
        membresia.setPlan(plan);
        membresia.setUsuario(usuarioCreador);

        return membresiaRepository.save(membresia);
    }

    @Override
    public MembresiaModel actualizar(Long id, MembresiaModel membresiaActualizada) {
        // En la mayoría de los casos, NO se permite actualizar una membresía
        // Solo se permite cancelarla o crear una nueva
        throw new UnsupportedOperationException("No se permite actualizar una membresía. Use cancelar() o cree una nueva.");
    }

    @Override
    public void cancelar(Long id) {
        MembresiaModel membresia = obtenerPorIdOrThrow(id);
        if (membresia.getEstadoMembresia() == EstadoMembresia.CANCELADA) {
            return; // Ya está cancelada
        }
        membresia.setEstadoMembresia(EstadoMembresia.CANCELADA);
        membresiaRepository.save(membresia);
    }

    // === REGLAS DE NEGOCIO ===

    private ClienteModel validarCliente(Long idCliente) {
        return clienteService.obtenerPorId(idCliente)
                .orElseThrow(() -> new MembresiaInvalida("Cliente no encontrado con ID: " + idCliente));
    }

    private PlanModel validarPlan(Long idPlan) {
        PlanModel plan = planService.obtenerPorId(idPlan)
                .orElseThrow(() -> new MembresiaInvalida("Plan no encontrado con ID: " + idPlan));

        if (plan.getEstado() != EstadoPlan.ACTIVO) {
            throw new MembresiaInvalida("No se puede usar un plan inactivo: " + plan.getNombre());
        }
        return plan;
    }

    private UsuarioModel validarUsuarioCreador(Long idUsuario) {
        UsuarioModel usuario = usuarioService.obtenerPorId(idUsuario)
                .orElseThrow(() -> new MembresiaInvalida("Usuario creador no encontrado."));

        if (!usuario.isActivo()) {
            throw new MembresiaInvalida("El usuario creador debe estar activo.");
        }
        return usuario;
    }

    private MembresiaModel obtenerPorIdOrThrow(Long id) {
        return membresiaRepository.findById(id)
                .orElseThrow(() -> new MembresiaNoEncontrada(id));
    }
}
