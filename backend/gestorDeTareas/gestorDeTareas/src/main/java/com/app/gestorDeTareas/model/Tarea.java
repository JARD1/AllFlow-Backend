package com.app.gestorDeTareas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Data
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String prioridad; // Ejemplo: Alta, Media, Baja
    private String dificultad; // Ejemplo: Fácil, Media, Difícil
    private String estado; // Ejemplo: Proceso, Completado

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaExpiracion;

    private String usuarioIp;

    // Este método asigna la fecha automáticamente antes de guardar en la DB
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}