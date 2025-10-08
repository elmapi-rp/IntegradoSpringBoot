package com.integrador.gym.Controller;

import com.integrador.gym.Exception.*;
import com.integrador.gym.Model.MembresiaModel;
import com.integrador.gym.Service.MembresiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    @GetMapping
    public List<MembresiaModel> listar() {
        return membresiaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembresiaModel> obtener(@PathVariable Long id) {
        return membresiaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MembresiaModel membresia) {
        try {
            MembresiaModel nueva = membresiaService.crear(membresia);
            return ResponseEntity.ok(nueva);
        } catch (ClienteYaTieneMembresiaActiva | MembresiaInvalida e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("Error interno: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/Cancelar")
    public ResponseEntity<Map<String, String>> cancelar(@PathVariable Long id) {
        try {
            membresiaService.cancelar(id);
            return ResponseEntity.noContent().build();
        } catch (MembresiaNoEncontrada e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("error al cancelar: " + e.getMessage()));
        }
    }

    private Map<String, String> errorResponse(String mensaje) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensaje);
        return response;
    }
}
