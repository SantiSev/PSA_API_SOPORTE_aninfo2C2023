package com.psa.soporte.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psa.soporte.enums.Estado;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tarea_id;
    private Long proyecto_id;
    private String nombre;
    private String nombre_asignado;
    private String descripcion;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_cierre;
    private Estado estado;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
