package com.integrador.gym.Service.Impl;

import com.integrador.gym.Dto.Creacion.MembresiaCreacionDTO;
import com.integrador.gym.Dto.MembresiaDto;
import com.integrador.gym.Exception.*;
import com.integrador.gym.Factory.MembresiaFactory;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.Enum.EstadoPlan;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Model.PlanModel;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Observer.MembresiaVencimiento;
import com.integrador.gym.Repository.MembresiaRepository;
import com.integrador.gym.Service.ClienteService;
import com.integrador.gym.Service.MembresiaService;
import com.integrador.gym.Service.PlanService;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MembresiaFactory membresiaFactory;

    @Autowired
    private List<MembresiaVencimiento> listeners;

    @Override
    public List<MembresiaModel> listarTodas() {
        return membresiaRepository.findAll();
    }

    @Override
    public Optional<MembresiaModel> obtenerPorId(Long id) {
        return membresiaRepository.findById(id);
    }

    @Override
    public MembresiaModel crear(MembresiaModel membresiaDTO) {

        ClienteModel cliente = validarCliente(membresiaDTO.getCliente().getIdCliente());
        PlanModel plan = validarPlan(membresiaDTO.getPlan().getIdPlan());
        UsuarioModel usuarioCreador = validarUsuarioCreador(membresiaDTO.getUsuario().getIdUsuario());

        if (membresiaRepository.existsByClienteIdClienteAndEstadoMembresia(
                cliente.getIdCliente(), EstadoMembresia.ACTIVA)) {
            throw new ClienteYaTieneMembresiaActiva(cliente.getIdCliente());
        }

        MembresiaModel nuevaMembresia = membresiaFactory.crearMembresia(
                cliente,
                plan,
                usuarioCreador,
                membresiaDTO.getFechaInicio()
        );

        return membresiaRepository.save(nuevaMembresia);
    }

    @Override
    public void actualizar(Long id, EstadoMembresia nuevoEstado) {
        MembresiaModel membresia = obtenerPorIdOrThrow(id);
        membresia.setEstadoMembresia(nuevoEstado);

        // Notificar a todos los listeners
        for (MembresiaVencimiento listener : listeners) {
            listener.onMembresiaPorVencer(membresia);
        }

        membresiaRepository.save(membresia);
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
    @Override
    public MembresiaDto crear(MembresiaCreacionDTO dto) {
        // Validar y obtener entidades
        ClienteModel cliente = validarCliente(dto.getIdCliente());
        PlanModel plan = validarPlan(dto.getIdPlan());
        UsuarioModel usuarioCreador = validarUsuarioCreador(dto.getIdUsuarioCreador());

        // Regla de negocio: un cliente solo puede tener una membresía ACTIVA
        if (membresiaRepository.existsByClienteIdClienteAndEstadoMembresia(
                cliente.getIdCliente(), EstadoMembresia.ACTIVA)) {
            throw new ClienteYaTieneMembresiaActiva(cliente.getIdCliente());
        }

        // Crear la membresía usando la fábrica
        MembresiaModel membresia = membresiaFactory.crearMembresia(
                cliente,
                plan,
                usuarioCreador,
                dto.getFechaInicio()
        );

        MembresiaModel guardada = membresiaRepository.save(membresia);

        // Convertir a DTO de salida
        return toDTO(guardada);
    }

    // Método auxiliar para mapear a DTO
    private MembresiaDto toDTO(MembresiaModel membresia) {
        MembresiaDto dto = new MembresiaDto();
        dto.setIdMembresia(membresia.getIdMembresia());
        dto.setIdCliente(membresia.getCliente().getIdCliente());
        dto.setNombreCliente(membresia.getCliente().getNombre() + " " + membresia.getCliente().getApellido());
        dto.setIdPlan(membresia.getPlan().getIdPlan());
        dto.setNombrePlan(membresia.getPlan().getNombre());
        dto.setFechaInicio(membresia.getFechaInicio());
        dto.setFechaFin(membresia.getFechaFin());
        dto.setEstadoMembresia(membresia.getEstadoMembresia());
        dto.setIdUsuarioCreador(membresia.getUsuario().getIdUsuario());
        dto.setNombreUsuarioCreador(membresia.getUsuario().getNombre() + " " + membresia.getUsuario().getApellido());
        return dto;
    }
}
