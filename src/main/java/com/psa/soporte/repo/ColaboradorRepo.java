package com.psa.soporte.repo;


import com.psa.soporte.modelos.Colaborador;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboradorRepo extends CrudRepository<Colaborador, Long> {

    Optional<Colaborador> findByLegajo(Long legajo);

    boolean existsByLegajo(@Param("legajo") Long legajo);

}
