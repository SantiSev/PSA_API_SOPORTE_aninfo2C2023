package com.psa.soporte.cucumber;

import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import com.psa.soporte.services.ProductoService;
import com.psa.soporte.services.TicketService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TicketOperacionesSteps extends CucumberBootstrap{



    private Long productoId;
    private Long versionId;
    private Long ticketId;
    private ProductoRequest productoRequest;
    private TicketRequest ticketRequest;
    private ProductoVersionRequest productoVersionRequest;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ProductoService productoService;

    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }

    @Before
    public void before() {
        log.info(">>> Before scenario!");
        ProductoResponse productoResponse = new ProductoResponse();
    }

    @Then("^El ticket se crea correctamente$")
    public void validacionDeCorrectaCreacionDeTicket(){
        TicketResponse ticketResponse = ticketService.getTicketById(ticketId);
        assertNotNull(ticketResponse);
        System.out.println("test");
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

        ticketService.actualizarTicket(ticketId, request);

        //Modificamos
        assertThrows(BadRequestException.class, () ->ticketService.actualizarTicket(ticketId, request));
    }

    @Given("^Creación correcta de un ticket vacío$")
    public void creacionDeProyecto()  {
        ticketRequest = new TicketRequest();
    }

    @When("^Se le asigna todo el contenido a dicho ticket para registrar un problema o tarea$")
    public void asignacionDeLosCamposDeUnProyecto() {
        try {
            ticketRequest.setNombre(null);
            ticketRequest.setDescripcion("Ticket basico");
            ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
            ticketRequest.setCategoria("inventado");
            ticketRequest.setPrioridad(Prioridad.BAJA.getDescripcion());
            ticketRequest.setSeveridad(Severidad.S1.getDescripcion());
            ticketRequest.setColaboradorId(null);
        } catch (RuntimeException err) {
            throw new RuntimeException();
        }
    }
    @Then("^El ticket queda cargado$")
    public TicketRequest elTicketSeCreaCorrectamente() {
        return ticketRequest;
    }


    @When("^Se le asigna todo el contenido a dicho ticket para registrar un problema$")
    public void asignacionDeLosCamposDeUnProyectoConErrores() {
        try {
            ticketRequest.setNombre(null);
            ticketRequest.setDescripcion("Ticket basico");
            ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
            ticketRequest.setCategoria("ERROR");
            ticketRequest.setPrioridad(Prioridad.MEDIA.getDescripcion());
            ticketRequest.setSeveridad(Severidad.S3.getDescripcion());
            ticketRequest.setColaboradorId(null);
        } catch (RuntimeException err) {
            throw new RuntimeException();
        }
    }

    @Then("^El ticket queda cargado con el error$")
    public TicketRequest elTicketDeErrorSeCreaCorrectamente() {
        return ticketRequest;
    }

    @Given("^A un ticket ya creado con sus atributos$")
    public void creacionDeTicketCargadoPreviamente()  {
        ticketRequest = new TicketRequest();
        ticketRequest.setNombre(null);
        ticketRequest.setDescripcion("Ticket basico");
        ticketRequest.setEstado(Estado.SIN_INICIAR.getEstado());
        ticketRequest.setCategoria("ERROR");
        ticketRequest.setPrioridad(Prioridad.MEDIA.getDescripcion());
        ticketRequest.setSeveridad(Severidad.S3.getDescripcion());
        ticketRequest.setColaboradorId(null);
    }

    @When("^Cuando se usa edita el estado del ticket$")
    public void cambiosdeEstadoDeUnTicketYaCreado() {
        try {
            ticketRequest.setEstado(Estado.EN_PROGRESO.getEstado());
        } catch (RuntimeException err) {
            throw new RuntimeException();
        }
    }
    @Then("^Se verá reflejado el cambio en el ticket$")
    public void elTicketSeModificaCorrectamente() {
        assertSame(ticketRequest.getEstado(), Estado.EN_PROGRESO.getEstado());
    }

    @When("^Cuando se usa edita la severidad del ticket$")
    public void cambiosdeSeveridadDeUnTicketYaCreado() {
        try {
            ticketRequest.setSeveridad(Severidad.S2.getDescripcion());
        } catch (RuntimeException err) {
            throw new RuntimeException();
        }
    }
    @Then("^Se verá reflejado el cambio en su severidad$")
    public void elTicketSeModificaCorrectamenteEnSuSeveridad() {
        assertSame(ticketRequest.getSeveridad(), Severidad.S2.getDescripcion());
    }


    @When("^Cuando se usa edita la prioridad del ticket")
    public void cambioEnlaPrioridadDelTicket() {
        try {
            ticketRequest.setPrioridad(Prioridad.ALTA.getDescripcion());
        } catch (RuntimeException err) {
            throw new RuntimeException();
        }
    }
    @Then("^Se verá reflejado el cambio en su prioridad$")
    public void func2() {
        assertSame(ticketRequest.getPrioridad(), Prioridad.ALTA.getDescripcion());
    }

}
