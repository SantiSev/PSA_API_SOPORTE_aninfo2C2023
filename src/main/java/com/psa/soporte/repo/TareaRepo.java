package com.psa.soporte.repo;


import com.psa.soporte.modelos.Tarea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TareaRepo extends CrudRepository<Tarea, Long> {


    Optional<Tarea> findByTareaIdRemoto(Long tareaIdRemoto);
}
