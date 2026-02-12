package com.app.gestorDeTareas.repository;

import com.app.gestorDeTareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    // Esto buscará solo las que SÍ estén 'COMPLETADA'
    List<Tarea> findByEstado(String estado);

    // Esto buscará solo las tareas que NO estén en estado 'COMPLETADA'
    List<Tarea> findByEstadoNot(String estado);

    List<Tarea> findByUsuarioID(String usuarioID);
}
