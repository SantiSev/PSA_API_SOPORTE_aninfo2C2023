package com.psa.soporte.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.services.ProductoService;
import com.psa.soporte.services.TicketService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.webjars.NotFoundException;


import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TicketOperacionesSteps extends CucumberBootstrap{


    private Long productoId;
    private Long versionId;
    private Long ticketId;
    private ProductoRequest productoRequest;
    private TicketRequest ticketRequest;
    private ProductoVersionRequest productoVersionRequest;

    private TicketService ticketService;
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

    @Given("^Existe una version de un producto, esta pertenece a un producto y se conoce su Id y el Id de la version$")
    public void creacionDeVersion() throws JsonProcessingException {
        creacionDeProducto();
        productoVersionRequest.setVersion("1.0.0");
        versionId = productoService.crearProductoVersion(productoId, productoVersionRequest).getProductoVersionId();
    }

    @When("^Se intenta crear un ticket con todos los campos asignados correctamente$")
    public void creacionCorrectaDeUnTicket() {
        //Arrange
        ticketRequest.setNombre("Ticket uno");
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
        ticketRequest.setCategoria(Categoria.PROYECTO.getCategoria());
        ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
        ticketRequest.setColaboradorId(null);
        ticketRequest.setTareaRequest(null);

        assertDoesNotThrow( () -> {ticketId = ticketService.crearTicket(ticketRequest, versionId).getTicketId();});
    }

    @When("^Se intenta asignar un ticket sin un titulo$")
    public void creacionDeTicketSinTitulo() {
        //Arrange
        ticketRequest.setNombre(null);
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
        ticketRequest.setCategoria(Categoria.PROYECTO.getCategoria());
        ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
        ticketRequest.setColaboradorId(null);
        ticketRequest.setTareaRequest(null);

        assertDoesNotThrow( () -> {ticketId = ticketService.crearTicket(ticketRequest, versionId).getTicketId();});
    }

    @When("^Se intenta asignar un ticket con un estado inexistente$")
    public void creacionDeTicketConEstadoInexistente() {
        //Arrange
        ticketRequest.setNombre(null);
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado("inventado");
        ticketRequest.setCategoria(Categoria.PROYECTO.getCategoria());
        ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
        ticketRequest.setColaboradorId(null);
        ticketRequest.setTareaRequest(null);

        assertDoesNotThrow( () -> {ticketId = ticketService.crearTicket(ticketRequest, versionId).getTicketId();});
    }

    @When("^Se intenta asignar un ticket con una categoria inexistente$")
    public void creacionDeTicketConCategoriaInexistente() {
        //Arrange
        ticketRequest.setNombre(null);
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
        ticketRequest.setCategoria("inventado");
        ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
        ticketRequest.setColaboradorId(null);
        ticketRequest.setTareaRequest(null);

        assertDoesNotThrow( () -> {ticketId = ticketService.crearTicket(ticketRequest, versionId).getTicketId();});
    }

    @When("^Se intenta crear una ticket con un estado inexistente$")
    public void creacionIncorrectaDeUnaTareaPorDescripcionEquivocada() {
        //Arrange
        ticketRequest.setNombre(null);
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado("inventado");
        ticketRequest.setCategoria(Categoria.PROYECTO.getCategoria());
        ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
        ticketRequest.setColaboradorId(null);
        ticketRequest.setTareaRequest(null);

        assertDoesNotThrow( () -> {ticketId = ticketService.crearTicket(ticketRequest, versionId).getTicketId();});
    }

    @When("^Se le intentan modificar algún campo del ticket con un dato inválido$")
    public void modificacionIncorrectaDeTarea() {

        TicketRequest request = new TicketRequest();
        request.setNombre(null);
        request.setDescripcion("Ticket basico");
        request.setEstado("inventado");
        request.setCategoria("inventado");
        request.setPrioridad("inventado");
        request.setSeveridad("inventado");
        request.setColaboradorId(null);
        request.setTareaRequest(null);

        ticketService.actualizarTicket(ticketId, request);

        //Modificamos
        assertThrows(BadRequestException.class, () ->ticketService.actualizarTicket(ticketId, request));
    }
    @When("^Se le intenta eliminar a la tarea$")
    public void EliminacionDeTicket(){
        ticketService.quitarTarea(ticketId);
    }

    @Then("^El ticket se crea correctamente$")
    public void validacionDeCorrectaCreacionDeTicket(){
        TicketResponse ticketResponse = ticketService.getTicketById(ticketId);
        assertNotNull(ticketResponse);
    }

    @Then("^El ticket no es creada, y se informa del error$")
    public void validacionDeIncorrectaCreacionDeTicket(){
        assertThrows(
                BadRequestException.class,
                () -> ticketService.crearTicket(ticketRequest, versionId)
        );
    }

    @Then("^La tarea es eliminada y ya no puede ser obtenida$")
    public void validacionTareaEliminada(){
        assertThrows(NotFoundException.class, () -> ticketService.getTicketById(ticketId));
    }

}
