package com.integrador.gym.Service.Impl;

import com.integrador.gym.Dto.Creacion.ClienteCreacionDTO;
import com.integrador.gym.Dto.ClienteDTO;
import com.integrador.gym.Exception.ClienteDniDuplicado;
import com.integrador.gym.Exception.ClienteNoEncontrado;
import com.integrador.gym.Exception.UsuarioNoAutorizado;
import com.integrador.gym.Factory.ClienteFactory;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Model.Enum.Roles;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Repository.ClienteRepository;
import com.integrador.gym.Service.ClienteService;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteFactory clienteFactory;

    @Override
    public List<ClienteModel> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<ClienteModel> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public ClienteDTO crear(ClienteCreacionDTO dto) {
        validarEdadMinima(dto.getFechaNacimiento());
        if (clienteRepository.existsByDni(dto.getDni())) {
            throw new ClienteDniDuplicado(dto.getDni());
        }
        UsuarioModel usuarioCreador = validarUsuarioCreador(dto.getIdUsuarioCreador());
        ClienteModel cliente = clienteFactory.crearDesdeDTO(dto, usuarioCreador);
        ClienteModel guardado = clienteRepository.save(cliente);
        return toDTO(guardado, usuarioCreador);
    }

    // Método para mapear a DTO
    private ClienteDTO toDTO(ClienteModel cliente, UsuarioModel usuarioCreador) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setDni(cliente.getDni());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        dto.setGenero(cliente.getGenero());
        dto.setIdUsuarioCreador(usuarioCreador.getIdUsuario());
        dto.setNombreUsuarioCreador(usuarioCreador.getNombre() + " " + usuarioCreador.getApellido());
        return dto;
    }

    @Override
    public ClienteModel actualizar(Long id, ClienteModel clienteActualizado) {
        ClienteModel existente = obtenerPorIdOrThrow(id);
        validarEdadMinima(clienteActualizado.getFechaNacimiento());

        if (clienteRepository.existsByDni(clienteActualizado.getDni())) {
            throw new ClienteDniDuplicado(clienteActualizado.getDni());
        }

        UsuarioModel nuevoUsuario = validarUsuarioCreador(clienteActualizado.getUsuario().getIdUsuario());
        existente.setDni(clienteActualizado.getDni());
        existente.setNombre(clienteActualizado.getNombre());
        existente.setApellido(clienteActualizado.getApellido());
        existente.setTelefono(clienteActualizado.getTelefono());
        existente.setDireccion(clienteActualizado.getDireccion());
        existente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        existente.setGenero(clienteActualizado.getGenero());
        existente.setUsuario(nuevoUsuario);

        return clienteRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNoEncontrado(id);
        }
        clienteRepository.deleteById(id);
    }
    private void validarEdadMinima(LocalDate fechaNacimiento) {
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 16) {
            throw new IllegalArgumentException("El cliente debe tener al menos 16 años.");
        }
    }
    private UsuarioModel validarUsuarioCreador(Long idUsuario) {
        Optional<UsuarioModel> usuarioOpt = usuarioService.obtenerPorId(idUsuario);
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().isActivo()) {
            throw new UsuarioNoAutorizado(
                    "No se puede registrar un cliente: el usuario creador no existe o está inactivo.");
        }
        UsuarioModel usuario = usuarioOpt.get();
        if (usuario.getRoles() != Roles.ADMIN && usuario.getRoles() != Roles.RECEPCIONISTA) {
            throw new UsuarioNoAutorizado(
                    "Solo los roles ADMIN o RECEPTION pueden registrar clientes."
            );
        }
        return usuario;
    }
    private ClienteModel obtenerPorIdOrThrow(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontrado(id));
    }

        @Override
    public boolean existePorDni(String dni) {
        return false;
    }
}
