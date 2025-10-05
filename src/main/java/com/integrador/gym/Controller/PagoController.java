package com.integrador.gym.Controller;

import com.integrador.gym.Dto.Actualizacion.PagoActualizacionDTO;
import com.integrador.gym.Dto.Creacion.PagoCreacionDTO;
import com.integrador.gym.Dto.PagoDTO;
import com.integrador.gym.Exception.*;
import com.integrador.gym.Model.PagoModel;
import com.integrador.gym.Service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/membresia/{idMembresia}")
    public List<PagoModel> listarPorMembresia(@PathVariable Long idMembresia) {
        return pagoService.listarPorMembresia(idMembresia);
    }

    @PostMapping

    public ResponseEntity<?> crear(@Valid @RequestBody PagoCreacionDTO dto) {
        try {
            PagoDTO nuevo = pagoService.crear(dto);
            return ResponseEntity.ok(nuevo);
        } catch (PagoInvalido e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PagoActualizacionDTO dto) {
        try {
            PagoDTO actualizado = pagoService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (PagoInvalido| PagoNoEncontrado e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            pagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (PagoNoEncontrado e) {
            return ResponseEntity.notFound().build();
        }
    }
}