package com.integrador.gym.Service.Impl;

import com.integrador.gym.Exception.DniYaRegistrado;
import com.integrador.gym.Exception.EmailYaRegistrado;
import com.integrador.gym.Exception.RolNoPermitido;
import com.integrador.gym.Exception.UsuarioNoEncontrado;
import com.integrador.gym.Model.Enum.Roles;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Repository.UsuarioRepository;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioModel> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public UsuarioModel crear(UsuarioModel usuario) {
        validarEdadMinima(usuario.getFechaNacimiento());
        validarUnicidad(usuario.getEmail(), usuario.getDni(), null);
        gestionarFechaContratacion(usuario);

        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioModel actualizar(Long id, UsuarioModel usuarioActualizado) {
        UsuarioModel existente = obtenerPorIdOrThrow(id);

        if (existente.getRoles() == Roles.CLIENTE && esEmpleado(usuarioActualizado.getRoles())) {
            throw new RolNoPermitido(
                    "No se puede convertir un cliente en empleado directamente. Use el flujo de contratación."
            );
        }
        validarEdadMinima(usuarioActualizado.getFechaNacimiento());
        validarUnicidad(usuarioActualizado.getEmail(), usuarioActualizado.getDni(), id);
        gestionarFechaContratacion(usuarioActualizado);
        //Campos que se actulizan
        existente.setDni(usuarioActualizado.getDni());
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setApellido(usuarioActualizado.getApellido());
        existente.setEmail(usuarioActualizado.getEmail());
        existente.setPassword(usuarioActualizado.getPassword());
        existente.setTelefono(usuarioActualizado.getTelefono());
        existente.setRoles(usuarioActualizado.getRoles());
        existente.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
        existente.setEstado(usuarioActualizado.getEstado());
        existente.setFechaContratacion(usuarioActualizado.getFechaContratacion());

        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    private void validarEdadMinima(LocalDate fechaNacimiento) {
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 16) {
            throw new IllegalArgumentException("El usuario debe tener al menos 16 años.");
        }
    }

    private void validarUnicidad(String email, String dni, Long idExcluido) {
        if (idExcluido == null) {
            if (usuarioRepository.existsByEmail(email)) {
                throw new EmailYaRegistrado(email);
            }
            if (usuarioRepository.existsByDni(dni)) {
                throw new DniYaRegistrado(dni);
            }
        } else {
            if (usuarioRepository.existsByEmailAndIdUsuarioNot(email, idExcluido)) {
                throw new EmailYaRegistrado(email);
            }
            if (usuarioRepository.existsByDniAndIdUsuarioNot(dni, idExcluido)) {
                throw new EmailYaRegistrado(dni);
            }
        }
    }

    private void gestionarFechaContratacion(UsuarioModel usuario) {
        if (esEmpleado(usuario.getRoles())) {
            if (usuario.getFechaContratacion() == null) {
                usuario.setFechaContratacion(LocalDate.now());
            }
        } else {
            usuario.setFechaContratacion(null); // Clientes no tienen fecha de contratación
        }
    }

    private boolean esEmpleado(Roles rol) {
        return rol == Roles.ADMIN || rol == Roles.RECEPCIONISTA;
    }

    private UsuarioModel obtenerPorIdOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontrado(id));
    }

    /*
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existePorDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    private void validarUnicidad(String email, String dni, Long idExcluido) {
        if (existePorEmail(email)) {
            if (idExcluido == null ||
                    !usuarioRepository.findById(idExcluido).map(u -> u.getEmail().equals(email)).orElse(false)) {
                throw new RuntimeException("El email ya está registrado: " + email);
            }
        }

        if (existePorDni(dni)) {
            if (idExcluido == null ||
                    !usuarioRepository.findById(idExcluido).map(u -> u.getDni().equals(dni)).orElse(false)) {
                throw new RuntimeException("El DNI ya está registrado: " + dni);
            }
        }
    }

     */
}
