package com.app.gestorDeTareas.controller;

import com.app.gestorDeTareas.model.Tarea;
import com.app.gestorDeTareas.repository.TareaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*") // Importante para que el Frontend pueda conectar
public class TareaController {

    @Autowired
    private TareaRepository repository;

    @GetMapping
    public List<Tarea> listar(HttpServletRequest request) {
        String ip = obtenerIpCliente(request);
        return repository.findByUsuarioIp(ip);
    }

    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea, HttpServletRequest request) {
        tarea.setUsuarioIp(obtenerIpCliente(request));
        return repository.save(tarea);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Tarea> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado, HttpServletRequest request) {
        String ip = obtenerIpCliente(request);
        return repository.findByIdAndUsuarioIp(id, ip)
                .map(t -> {
                    t.setEstado(nuevoEstado);
                    return ResponseEntity.ok(repository.save(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Método auxiliar para obtener la IP real (incluso tras un proxy como Nginx)
    private String obtenerIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, HttpServletRequest request) {
        String ip = obtenerIpCliente(request);
        return repository.findByIdAndUsuarioIp(id, ip)
                .map(t -> {
                    repository.delete(t);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}