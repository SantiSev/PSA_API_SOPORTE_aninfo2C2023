package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.ProductoVersionResponse;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Colaborador;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.services.ClienteService;
import com.psa.soporte.services.ColaboradorService;
import com.psa.soporte.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "developer")
public class DeveloperController {

    private final ClienteService clienteService;
    private final ColaboradorService colaboradorService;
    private final ProductoService productoService;


    public DeveloperController(ClienteService clienteService, ColaboradorService colaboradorService, ProductoService productoService) {
        this.clienteService = clienteService;
        this.colaboradorService = colaboradorService;
        this.productoService = productoService;
    }


    @GetMapping("cliente/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByClient(@PathVariable Long id) {
        List<Ticket> tickets = clienteService.getAllTicketsFromClient(id);
        return tickets != null ?
                new ResponseEntity<>(tickets, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("cliente")
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteRequest cliente) {
        Cliente createdCliente = clienteService.crearCliente(cliente);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    @PostMapping("procesar_clientes")
    public ResponseEntity<List<Cliente>> procesarClientes() {
        List<Cliente> clientes = clienteService.procesarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.CREATED);
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
        Cliente clienteActualizada = clienteService.actualizarCliente(id, clienteRequest);
        return clienteActualizada != null ?
                new ResponseEntity<>(clienteActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<Void> quitarCliente(@PathVariable Long id) {
        clienteService.quitarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("colaborador/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByColaborador(@PathVariable Long id) {
        List<Ticket> tickets = colaboradorService.getAllTicketsByColaborador(id);
        return tickets != null ?
                new ResponseEntity<>(tickets, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("colaborador")
    public ResponseEntity<Colaborador> crearColaborador(@RequestBody ColaboradorRequest colaborador) {
        Colaborador colaboradorCreado = colaboradorService.crearColaborador(colaborador);
        return new ResponseEntity<>(colaboradorCreado, HttpStatus.CREATED);
    }


    @PutMapping("colaborador/{id}")
    public ResponseEntity<Colaborador> actualizarColaborador(@PathVariable Long id, @RequestBody ColaboradorRequest colaboradorRequest) {
        Colaborador coalboradorActualizada = colaboradorService.actualizarColaborador(id, colaboradorRequest);
        return coalboradorActualizada != null ?
                new ResponseEntity<>(coalboradorActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("colaborador/{id}")
    public ResponseEntity<Void> quitarColaborador(@PathVariable Long id) {
        colaboradorService.quitarColaborador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("procesar_colaboradores")
    public ResponseEntity<List<Colaborador>> procesarColaboradores() {
        List<Colaborador> colaboradors = colaboradorService.procesarColaboradores();
        return new ResponseEntity<>(colaboradors, HttpStatus.CREATED);
    }

    @GetMapping("producto/{id}")
    public ResponseEntity<ProductoResponse> getProductoById(@PathVariable Long id) {
        ProductoResponse producto = productoService.getProductoById(id);
        return producto != null ?
                new ResponseEntity<>(producto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("producto")
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoRequest producto) {
        ProductoResponse crearProducto = productoService.crearProducto(producto);
        return new ResponseEntity<>(crearProducto, HttpStatus.CREATED);
    }

   @PostMapping("producto/{id}")
    public ResponseEntity<ProductoVersionResponse> crearProductoVersion(@PathVariable Long id, @RequestBody ProductoVersionRequest version) {
        ProductoVersionResponse crearProductoVersion = productoService.crearProductoVersion(id, version);
        return new ResponseEntity<>(crearProductoVersion, HttpStatus.CREATED);
    }

   @PutMapping("producto/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest ProductoRequest) {
        ProductoResponse actualizarProducto = productoService.actualizarProducto(id, ProductoRequest);
        return actualizarProducto != null ?
                new ResponseEntity<>(actualizarProducto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("producto/version/{id}")
    public ResponseEntity<ProductoVersionResponse> actualizarVersion(@PathVariable Long id, @RequestBody ProductoVersionRequest ProductoVersionRequest) {
        ProductoVersionResponse actualizarVersion = productoService.actualizarVersion(id, ProductoVersionRequest);
        return actualizarVersion != null ?
                new ResponseEntity<>(actualizarVersion, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("producto/{id}")
    public ResponseEntity<Void> quitarProducto(@PathVariable Long id) {
        productoService.quitarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("producto/version/{id}")
    public ResponseEntity<Void> quitarVersion(@PathVariable Long id) {
        productoService.quitarProductoVersion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("prodcuto_dummy_data")
    public List<ProductoResponse> crearDummyData(){

        List<String> productoNombres = new ArrayList<>(Arrays.asList("Sistema de Gestion Ultimatum", "Sistema de Seguridad ASAP", "Mega Sistemas"));
        List<String> versiones = List.of("1.0.0", "2.1.3", "3.5.2", "4.2.1", "5.3.0");

        List<ProductoResponse> responses = new ArrayList<>();

        for (String nombre: productoNombres) {
            ProductoRequest request = new ProductoRequest();
            request.setNombre(nombre);
            ProductoResponse productoNuevo = productoService.crearProducto(request);

            for (String version: versiones) {
                ProductoVersionRequest versionRequest = new ProductoVersionRequest();
                versionRequest.setVersion(version);
                productoService.crearProductoVersion(productoNuevo.getProductoId(), versionRequest);
            }

            responses.add(productoNuevo);
        }

        return responses;
    }

}
