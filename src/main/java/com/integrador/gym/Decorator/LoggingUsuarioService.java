package com.integrador.gym.Decorator;

import com.integrador.gym.Dto.Actualizacion.UsuarioActualizacionDTO;
import com.integrador.gym.Dto.Creacion.UsuarioCreacionDTO;
import com.integrador.gym.Dto.UsuarioDTO;
import com.integrador.gym.Model.UsuarioModel;
import com.integrador.gym.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary @Component @Service
public class LoggingUsuarioService implements UsuarioService {

    @Autowired
    private UsuarioService delegate;
    // Servicio real
    public LoggingUsuarioService(@Qualifier("usuarioServiceImpl") UsuarioService delegate) {
        this.delegate = delegate;
    }
    @Override
    public List<UsuarioModel> listarTodos() {
        System.out.println("Llamando a listarTodos()");
        return delegate.listarTodos();
    }

    @Override
    public Optional<UsuarioModel> obtenerPorId(Long id) {
        System.out.println("LOG: Obteniendo usuario con ID " + id);
        return delegate.obtenerPorId(id);

    }

    @Override
    public UsuarioDTO crear(UsuarioCreacionDTO dto) {
        System.out.println("Creando usuario: " + dto.getNombre());
        return delegate.crear(dto);
    }

    @Override
    public UsuarioDTO actualizar(Long id, UsuarioActualizacionDTO dto) {
        System.out.println("LOG: Actualizando usuario con ID " + id);
        return delegate.actualizar(id, dto);
    }

    @Override
    public void eliminar(Long id) {
        System.out.println("LOG: Eliminando usuario con ID " + id);
        delegate.eliminar(id);
    }
}
