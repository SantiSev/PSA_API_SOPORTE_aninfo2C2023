package com.psa.soporte.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.services.ProductoService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;


import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProductoOperacionesSteps extends CucumberBootstrap{


    private Long productoId;
    private ProductoRequest productoRequest;
    private ProductoVersionRequest productoVersionRequest;

    private ProductoService productoService;

    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }

    //this method executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        ProductoResponse productoResponse = new ProductoResponse();
    }

    //TEST =======================================================================


    @Given("^Que existe un producto y conozco su Id$")
    public void creacionDeProducto() throws JsonProcessingException {
        if (productoRequest == null) {
            ProductoRequest request = new ProductoRequest();
            request.setNombre("Sistema de Gestion");
            productoId = productoService.crearProducto(request).getProductoId();
        }
    }

    @Given("^Que tengo un id de un producto que no existe$")
    public void asignacionDeIdIncorrectoAProyecto(){
        productoId = 8573L;
    }

    @Given("^Existe una version de un producto, esta pertenece a un producto y se conoce su Id y el Id de su version$")
    public void creacionDeVersion() throws JsonProcessingException {
        creacionDeProducto();
        productoVersionRequest.setVersion();

        tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;
    }

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente$")
    public void creacionCorrectaDeUnaTarea() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
        tareaDto.setFechaInicio(Date.valueOf("2015-12-12"));
        tareaDto.setFechaFin(Date.valueOf("2022-02-02"));

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente menos las fechas$")
    public void creacionCorrectaDeUnaTareaSinFechas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea sin indicar la descripción correctamente$")
    public void creacionIncorrectaDeUnaTareaPorDescripcionEquivocada() {
        //Arrange
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^Se intenta crear una tarea sin indicar un estado correctamente$")
    public void creacionIncorrectaDeUnaTareaPorEstadoVacio() {
        //Arrange
        tareaDto.setDescripcion("Hola reyes");
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^se intenta crear una tarea con una fecha final anterior a su fecha de inicio$")
    public void creacionIncorrectaDeUnaTareaPorFechasIncorrectas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
        tareaDto.setFechaFin(Date.valueOf("2015-12-12"));
        tareaDto.setFechaInicio(Date.valueOf("2022-02-02"));
    }

    @When(("^Se intenta crear una tarea para dicho proyecto$"))
    public void creacionIncorrectaDeUnaTareaPorIdDeproyectoIncorrecto(){
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^Se le intentan modificar algún campo de la tarea con un dato válido$")
    public void modificacionCorrectaDeTarea() throws JsonProcessingException {
        TareaDto tareaModificada = new TareaDto();

        tareaModificada.setDescripcion("descripcionModificada");
        tareaModificada.setFechaInicio(Date.valueOf("2010-10-10"));
        tareaModificada.setFechaFin(Date.valueOf("2023-03-03"));
        tareaModificada.setEstadoIdm(TareaEstado.EN_CURSO_IDM);
        tareaModificada.setColaboradorAsignadoId(2L);

        //Modificamos

        proyectoService.updateTarea(tareaModificada, proyectoId, tareaId);
    }

    @When("^Se le intentan modificar algún campo de la tarea con un dato inválido$")
    public void modificacionIncorrectaDeTarea() {
        TareaDto tareaModificada = new TareaDto();

        tareaModificada.setDescripcion(null);
        tareaModificada.setFechaInicio(null);
        tareaModificada.setFechaFin(Date.valueOf("2023-03-03"));
        tareaModificada.setEstadoIdm(TareaEstado.EN_CURSO_IDM);
        tareaModificada.setColaboradorAsignadoId(2L);

        //Modificamos
        assertThrows(TareaInvalidaException.class, () ->proyectoService.updateTarea(tareaModificada, proyectoId, tareaId));
    }
    @When("^Se le intenta eliminar a la tarea$")
    public void EliminacionDeTarea(){
        tareaService.deleteTareaById(tareaId);
    }

    @Then("^La tarea se crea correctamente$")
    public void validacionDeCorrectaCreacionDeTarea(){
        ArrayList<Tarea> tareasObtenidas = proyectoService.getTareasByProyectoId(proyectoId);
        Tarea tareaFinal = tareasObtenidas.stream().filter(t -> tareaId == t.id).findAny().orElse(null);
        assertNotNull(tareaFinal);
    }

    @Then("^La tarea no es creada, y se informa del error$")
    public void validacionDeIncorrectaCreacionDeTarea(){
        assertThrows(
                TareaInvalidaException.class,
                () -> proyectoService.saveTarea(tareaDto, proyectoId)
        );
    }

    @Then("^La tarea no es creada porque el proyecto no existe$")
    public void validacionDeQueLaTareaNoHaSidoCreadaPorProyectoInexistente(){
        assertThrows(
                TareaInvalidaException.class,
                () -> proyectoService.saveTarea(tareaDto, proyectoId)
        );
    }
    
    @Then("^La tarea se actualiza y ahora tiene sus campos modificados$")
    public void validacionDeModificacionCorrectaDeTarea(){
        Tarea tareaModificada = proyectoService.getTareasByProyectoId(proyectoId).stream().filter(
                tarea -> tareaId == tarea.id
        ).findAny().orElse(null);

        assertEquals(tareaModificada.descripcion, "descripcionModificada");
        assertEquals(tareaModificada.estado.idm, ProyectoEstado.EN_PROGRESO_IDM);
        assertEquals(tareaModificada.fechaInicio, Date.valueOf("2010-10-10"));
        assertEquals(tareaModificada.fechaFin, Date.valueOf("2023-03-03"));
        assertEquals(tareaModificada.colaboradorAsignadoId, 2L);
    }

    @Then("^La tarea no se actualiza y se recibe una excepción$")
    public void validacionDeModificacionIncorrectaDeTarea(){
        Tarea tareaModificada = proyectoService.getTareasByProyectoId(proyectoId).stream().filter(
                tarea -> tareaId == tarea.id
        ).findAny().orElse(null);

        assertEquals(tareaModificada.descripcion, tareaDto.getDescripcion());
        assertEquals(tareaModificada.estado.idm, tareaDto.getEstadoIdm());
        assertEquals(tareaModificada.fechaInicio, tareaDto.getFechaInicio());
        assertEquals(tareaModificada.fechaFin, tareaDto.getFechaFin());
        assertEquals(tareaModificada.colaboradorAsignadoId, tareaDto.getColaboradorAsignadoId());
    }

    @Then("^La tarea es eliminada y ya no puede ser obtenida$")
    public void validacionTareaEliminada(){
        assertThrows(TareaNoEncontradaException.class, () -> tareaService.getTareaById(tareaId));
    }

}
