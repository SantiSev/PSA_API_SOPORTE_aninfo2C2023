package com.psa.soporte.modelos;


import com.psa.soporte.DTO.request.ClienteRequest;
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
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_id;

    @Column
    private String razonSocial;

    @Column
    private String CUIT;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.DETACH)
    private List<Ticket> tickets;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Cliente(ClienteRequest clienteRequest) {
        this.razonSocial = clienteRequest.getRazonSocial();
        this.CUIT = clienteRequest.getCUIT();
        this.tickets = new ArrayList<Ticket>();
    }
}
