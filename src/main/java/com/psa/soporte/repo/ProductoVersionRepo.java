package com.psa.soporte.repo;


import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.ProductoVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoVersionRepo extends CrudRepository<ProductoVersion, Long> {

    boolean existsByVersion(@Param("version") String legajo);


}
