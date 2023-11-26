package com.psa.soporte.repo;


import com.psa.soporte.modelos.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepo extends CrudRepository<Producto, Long> {

}
