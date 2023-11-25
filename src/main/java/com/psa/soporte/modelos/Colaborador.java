package com.psa.soporte.modelos;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "colaboradores")
@Data
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

}
