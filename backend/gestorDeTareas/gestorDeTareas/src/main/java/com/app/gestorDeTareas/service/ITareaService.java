package com.app.gestorDeTareas.service;

import com.app.gestorDeTareas.model.Tarea;
import java.util.List;

public interface ITareaService {
    List<Tarea> listarPendientes();
    List<Tarea> listarTerminadas();
    Tarea guardarTarea(Tarea tarea);
    Tarea cambiarEstado(Long id, String nuevoEstado);
    void eliminarTarea(Long id);
    List<Tarea> listarTareas();
}