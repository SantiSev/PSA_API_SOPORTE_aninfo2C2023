package com.psa.soporte.repo;


import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Colaborador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepo extends CrudRepository<Colaborador, Long> {

}
