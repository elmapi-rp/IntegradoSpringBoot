package com.integrador.gym.Repository;

import com.integrador.gym.Model.Enum.EstadoMembresia;
import com.integrador.gym.Model.MembresiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MembresiaRepository  extends JpaRepository<MembresiaModel, Long> {

    boolean existsByClienteIdClienteAndEstadoMembresia(Long idCliente, EstadoMembresia estado);

    List<MembresiaModel> findByClienteIdClienteAndEstadoMembresia(Long idCliente, EstadoMembresia estado);

    List<MembresiaModel> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndEstadoMembresia(
            LocalDate fecha, LocalDate fecha2, EstadoMembresia estado);
}
