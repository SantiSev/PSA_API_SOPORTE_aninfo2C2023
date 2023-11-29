package com.psa.soporte.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import com.psa.soporte.tools.Validacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_id;
    private Long tarea_id;
    private String nombre;
    private String descripcion;

    private Prioridad prioridad;

    private Severidad severidad;

    private Categoria categoria;

    private Estado estado;

    public Ticket(TicketRequest ticketRequest) {
        this.nombre = ticketRequest.getNombre();
        this.descripcion = ticketRequest.getDescripcion();

        Validacion.validarEnum(ticketRequest.getPrioridad(), Prioridad.class);
        this.prioridad = Prioridad.valueOf(ticketRequest.getPrioridad().toUpperCase());

        Validacion.validarEnum(ticketRequest.getSeveridad(), Severidad.class);
        this.severidad = Severidad.valueOf(ticketRequest.getSeveridad().toUpperCase());

        Validacion.validarEnum(ticketRequest.getCategoria(), Categoria.class);
        this.categoria = Categoria.valueOf(ticketRequest.getCategoria().toUpperCase());

        Validacion.validarEnum(ticketRequest.getEstado(), Estado.class);
        this.estado = Estado.valueOf(ticketRequest.getEstado().toUpperCase());

    }

    @JsonIgnore
    @ManyToOne
    private Colaborador colaborador;

    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    @JsonIgnore
    @ManyToOne
    private Producto producto;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
