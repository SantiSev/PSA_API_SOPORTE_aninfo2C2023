package com.psa.soporte.modelos;


import com.psa.soporte.DTO.request.ProductoRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producto_id;

    private Long proyecto_id;

    @Column
    private String version;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "product_client",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Cliente> clientes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Producto(ProductoRequest productoRequest) {
        this.proyecto_id = productoRequest.getProyecto_id();
        this.version = productoRequest.getVersion();
    }
}
