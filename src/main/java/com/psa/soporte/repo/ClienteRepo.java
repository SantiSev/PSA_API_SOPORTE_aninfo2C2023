package com.psa.soporte.repo;


import com.psa.soporte.modelos.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepo extends CrudRepository<Cliente, Long> {

    boolean existsByRazonSocial(String razon_social);

    boolean existsByCUIT(String CUIT);

}
