package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.ExceptionMensajes;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.ProductoRepo;
import com.psa.soporte.tools.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepo productoRepo;
    private final ClienteService clienteService;

    @Autowired
    public ProductoService(ProductoRepo productoRepo, ClienteService clienteService) {
        this.productoRepo = productoRepo;
        this.clienteService = clienteService;
    }


    public List<ProductoResponse> getAllProductos() {
        List<Producto> productos = (List<Producto>) productoRepo.findAll();
        if (productos.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToProductoResponseList(productos);
    }

    public ProductoResponse getProductoById(Long id) {
        return Converter.convertToProductoResponse(productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage())));
    }


    public List<TicketResponse> getAllTicketsByProducto(Long id) {
        Producto colaborador = productoRepo.findById(id).orElse(null);
        assert colaborador != null;
        if (colaborador.getTickets().isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(colaborador.getTickets());
    }

    public ProductoResponse crearProducto(ProductoRequest productoRequest) {
   
        Producto productoNuevo = new Producto(productoRequest);
        return Converter.convertToProductoResponse(productoRepo.save(productoNuevo));
    }

    public ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequestRequest) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        if (productoRequestRequest.getProyecto_id() != null) {
            producto.setProducto_id(productoRequestRequest.getProyecto_id());
        }
        if (productoRequestRequest.getVersion() != null) {
            producto.setVersion(productoRequestRequest.getVersion());
        }

        return Converter.convertToProductoResponse(productoRepo.save(producto));
    }

    public ProductoResponse asignarClienteAProducto(Long prodcuto_id, Long cliente_id){
        Cliente cliente = clienteService.getClienteById(cliente_id);
        Producto producto = productoRepo.findById(prodcuto_id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        producto.getClientes().add(cliente);

        return Converter.convertToProductoResponse(productoRepo.save(producto));
    }

    public ProductoResponse quitarClienteDeProducto(Long prodcuto_id, Long cliente_id){
        Cliente cliente = clienteService.getClienteById(cliente_id);
        Producto producto = productoRepo.findById(prodcuto_id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        producto.getClientes().remove(cliente);

        return Converter.convertToProductoResponse(productoRepo.save(producto));

    }

    public void quitarProducto(Long id) {
        productoRepo.deleteById(id);
    }

}
