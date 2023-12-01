package com.psa.soporte.repo;


import com.psa.soporte.modelos.Tarea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepo extends CrudRepository<Tarea, Long> {

}
