package com.integrador.gym.Controller;

import com.integrador.gym.Dto.Creacion.ClienteCreacionDTO;
import com.integrador.gym.Dto.ClienteDTO;
import com.integrador.gym.Exception.ClienteDniDuplicado;
import com.integrador.gym.Exception.ClienteNoEncontrado;
import com.integrador.gym.Exception.UsuarioNoAutorizado;
import com.integrador.gym.Model.ClienteModel;
import com.integrador.gym.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtener(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ClienteCreacionDTO dto) {
        try {
            ClienteDTO nuevo = clienteService.crear(dto);
            return ResponseEntity.ok(nuevo);
        } catch (ClienteNoEncontrado | UsuarioNoAutorizado e) {
            return ResponseEntity.badRequest().body(errorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(errorResponse("Validación: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorResponse("Error interno: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteModel datos) {
        try {
            ClienteModel actualizado = clienteService.actualizar(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (ClienteNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ClienteDniDuplicado | UsuarioNoAutorizado e) {
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
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (ClienteNoEncontrado e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, String> errorResponse(String mensaje) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensaje);
        return response;
    }
}
