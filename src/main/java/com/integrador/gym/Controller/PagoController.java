package com.integrador.gym.Controller;

import com.integrador.gym.Exception.PagoInvalido;
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
    public ResponseEntity<?> crear(@Valid @RequestBody PagoModel pago) {
        try {
            PagoModel nuevo = pagoService.crear(pago);
            return ResponseEntity.ok(nuevo);
        } catch (PagoInvalido e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("Error interno: " + e.getMessage()));
        }
    }

    private Map<String, String> errorResponse(String mensaje) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensaje);
        return response;
    }
}