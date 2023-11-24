package com.psa.soporte.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_id;

    private String nombre;
    private String descripcion;

    private Prioridad prioridad;

    private Severidad severidad;

    private Categoria categoria;

    private Estado estado;

    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tarea_id")
    private Tarea tarea;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
