package com.app.gestorDeTareas.service;

import com.app.gestorDeTareas.model.Tarea;
import com.app.gestorDeTareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TareaServiceImpl implements ITareaService {

    @Autowired
    private TareaRepository repository;

    @Override
    public List<Tarea> listarPendientes() {
        return repository.findByEstadoNot("COMPLETADA");
    }

    @Override
    public List<Tarea> listarTerminadas() {
        return repository.findByEstado("COMPLETADA");
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return repository.save(tarea);
    }

    @Override
    public Tarea cambiarEstado(Long id, String nuevoEstado) {
        Tarea tarea = repository.findById(id).orElseThrow();
        tarea.setEstado(nuevoEstado);
        return repository.save(tarea);
    }

    @Override
    public void eliminarTarea(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Tarea> listarTareas(){
        return repository.findAll();
    }

}