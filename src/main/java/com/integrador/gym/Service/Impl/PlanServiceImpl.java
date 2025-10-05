package com.integrador.gym.Service.Impl;

import com.integrador.gym.Exception.PlanInvalido;
import com.integrador.gym.Exception.PlanNoEncontrado;
import com.integrador.gym.Exception.PlanNombreDuplicado;
import com.integrador.gym.Model.Enum.EstadoPlan;
import com.integrador.gym.Model.PlanModel;
import com.integrador.gym.Repository.PlanRepository;
import com.integrador.gym.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Override
    public List<PlanModel> listarTodos() {
        return planRepository.findAll();
    }

    @Override
    public List<PlanModel> listarActivos() {
        return planRepository.findByEstado(EstadoPlan.ACTIVO);
    }

    @Override
    public Optional<PlanModel> obtenerPorId(Long id) {
        return planRepository.findById(id);
    }

    @Override
    public PlanModel crear(PlanModel plan) {
        validarPlan(plan);
        asegurarNombreUnico(plan.getNombre(), null);
        return planRepository.save(plan);
    }

    @Override
    public PlanModel actualizar(Long id, PlanModel planActualizado) {
        PlanModel existente = obtenerPorIdOrThrow(id);

        validarPlan(planActualizado);
        asegurarNombreUnico(planActualizado.getNombre(), id);

        existente.setNombre(planActualizado.getNombre());
        existente.setDescripcion(planActualizado.getDescripcion());
        existente.setPrecio(planActualizado.getPrecio());
        existente.setDuracionDias(planActualizado.getDuracionDias());
        existente.setBeneficios(planActualizado.getBeneficios());
        existente.setEstado(planActualizado.getEstado());

        return planRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        PlanModel plan = obtenerPorIdOrThrow(id);
        plan.setEstado(EstadoPlan.INACTIVO);
        planRepository.save(plan);
    }

    // === REGLAS DE NEGOCIO ===

    private void validarPlan(PlanModel plan) {
        if (plan.getPrecio() == null || plan.getPrecio().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new PlanInvalido("El precio del plan debe ser mayor a 0.");
        }
        if (plan.getDuracionDias() == null || plan.getDuracionDias() <= 0) {
            throw new PlanInvalido("La duración del plan debe ser al menos 1 día.");
        }
        if (plan.getNombre() == null || plan.getNombre().trim().isEmpty()) {
            throw new PlanInvalido("El nombre del plan no puede estar vacío.");
        }
    }

    private void asegurarNombreUnico(String nombre, Long idExcluido) {
        if (idExcluido == null) {
            if (planRepository.existsByNombre(nombre)) {
                throw new PlanNombreDuplicado(nombre);
            }
        } else {
            if (planRepository.existsByNombreAndIdPlanNot(nombre, idExcluido)) {
                throw new PlanNombreDuplicado(nombre);
            }
        }
    }

    private PlanModel obtenerPorIdOrThrow(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new PlanNoEncontrado(id));
    }
}
