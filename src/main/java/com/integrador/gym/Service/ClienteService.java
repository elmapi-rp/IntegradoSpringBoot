package com.integrador.gym.Service;

import com.integrador.gym.Model.ClienteModel;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<ClienteModel> listarTodos();
    Optional<ClienteModel> obtenerPorId(Long id);
    ClienteModel crear(ClienteModel cliente);
    ClienteModel actualizar(Long id, ClienteModel clienteActualizado);
    void eliminar(Long id);
    boolean existePorDni(String dni);
}
