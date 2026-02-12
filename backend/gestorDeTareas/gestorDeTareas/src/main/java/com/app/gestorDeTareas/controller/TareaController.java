package com.app.gestorDeTareas.controller;

import com.app.gestorDeTareas.model.Tarea;
import com.app.gestorDeTareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*") // Permite que el Frontend conecte con Render
public class TareaController {

    @Autowired
    private TareaRepository repository;

    // LISTAR: Filtra por el ID que envía el navegador
    @GetMapping
    public List<Tarea> listar(@RequestParam String usuarioId) {
        return repository.findByUsuarioID(usuarioId);
    }

    // CREAR: Recibe el objeto tarea con el usuarioID ya incluido desde el JS
    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        return repository.save(tarea);
    }

    // ACTUALIZAR ESTADO: Verifica que la tarea pertenezca al usuarioID
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Tarea> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestParam String usuarioId) {

        return repository.findById(id)
                .map(t -> {
                    // Verificación de seguridad: ¿Es el dueño de la tarea?
                    if (!t.getUsuarioID().equals(usuarioId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).<Tarea>build();
                    }
                    t.setEstado(nuevoEstado);
                    return ResponseEntity.ok(repository.save(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR: Solo permite borrar si el usuarioId coincide
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @RequestParam String usuarioId) {

        return repository.findById(id)
                .map(t -> {
                    if (!t.getUsuarioID().equals(usuarioId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).<Void>build();
                    }
                    repository.delete(t);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}