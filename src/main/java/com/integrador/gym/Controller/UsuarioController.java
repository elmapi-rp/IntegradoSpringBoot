package com.integrador.gym.Controller;

import com.integrador.gym.Exception.DniYaRegistrado;
import com.integrador.gym.Exception.EmailYaRegistrado;
import com.integrador.gym.Exception.RolNoPermitido;
import com.integrador.gym.Exception.UsuarioNoEncontrado;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioModel usuario) {
        try {
            UsuarioModel nuevo = usuarioService.crear(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (EmailYaRegistrado | DniYaRegistrado e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(errorResponse("Validación: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("Error interno: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioModel datos) {
        try {
            UsuarioModel actualizado = usuarioService.actualizar(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (UsuarioNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (EmailYaRegistrado | DniYaRegistrado | RolNoPermitido e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(errorResponse("Validación: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("Error interno: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, String> errorResponse(String mensaje) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensaje);
        return response;
    }
}
