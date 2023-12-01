package com.psa.soporte.modelos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
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
@Table(name = "productoVersiones")
public class ProductoVersion {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoVersionId;

    @Column
    private String version;

    @JsonIgnore
    @ManyToOne
    private Producto producto;

    @OneToMany(mappedBy = "productoVersion", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductoVersion(ProductoVersionRequest productoVersionRequest) {
        this.version = productoVersionRequest.getVersion();
        this.tickets = new ArrayList<Ticket>();
    }
}
