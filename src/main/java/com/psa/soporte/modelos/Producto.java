package com.psa.soporte.modelos;


import com.psa.soporte.DTO.request.ProductoRequest;
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
@Table(name = "productos")
public class Producto {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoVersion> versiones;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Producto(ProductoRequest productoRequest) {
        this.nombre = productoRequest.getNombre();
        this.versiones = new ArrayList<>();
    }
}
