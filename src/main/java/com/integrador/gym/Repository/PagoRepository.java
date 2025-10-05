package com.integrador.gym.Repository;

import com.integrador.gym.Exception.PagoNoEncontrado;
import com.integrador.gym.Model.PagoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoModel, Long> {

    List<PagoModel> findByMembresiaIdMembresia(Long idMembresia);

    long countByMembresiaIdMembresia(Long idMembresia);
}
