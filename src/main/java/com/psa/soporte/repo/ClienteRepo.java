package com.psa.soporte.repo;


import com.psa.soporte.modelos.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepo extends CrudRepository<Cliente, Long> {

    Optional<Cliente> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
