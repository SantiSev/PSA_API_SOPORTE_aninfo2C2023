package com.psa.soporte.modelos;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int client_id;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.DETACH)
    private List<Ticket> ticket;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
