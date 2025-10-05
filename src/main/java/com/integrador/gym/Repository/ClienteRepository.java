package com.integrador.gym.Repository;

import com.integrador.gym.Model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    boolean existsByDni(String dni);
    boolean existsByDniAndIdClienteNot(String dni, Long id);
    long countByUsuarioIdUsuario(Long idUsuario);
}
