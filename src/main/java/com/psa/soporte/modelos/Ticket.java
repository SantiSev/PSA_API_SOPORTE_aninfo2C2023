package com.psa.soporte.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import com.psa.soporte.tools.Validacion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private Prioridad prioridad;
    @Column
    private Severidad severidad;
    @Column
    private Categoria categoria;
    @Column
    private Estado estado;

    @JsonIgnore
    @ManyToOne
    private Colaborador colaborador;

    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    @JsonIgnore
    @ManyToOne
    private ProductoVersion productoVersion;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
