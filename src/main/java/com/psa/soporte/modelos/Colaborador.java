package com.psa.soporte.modelos;


import com.psa.soporte.DTO.request.ColaboradorRequest;
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
@Table(name = "colaboradores")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colaborador_id;

    private String nombre;

    @Column(unique = true)
    private Long legajo;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.DETACH)
    private List<Ticket> tickets;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Colaborador(ColaboradorRequest colaboradorRequest) {
        this.nombre = colaboradorRequest.getNombre();
        this.legajo = Long.valueOf(colaboradorRequest.getLegajo());
        this.tickets = new ArrayList<Ticket>();
    }
}
