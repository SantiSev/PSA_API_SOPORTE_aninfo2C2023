package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.ProductoVersionResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.ExceptionMensajes;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.ProductoVersion;
import com.psa.soporte.repo.ProductoRepo;
import com.psa.soporte.repo.ProductoVersionRepo;
import com.psa.soporte.tools.Converter;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepo productoRepo;
    private final ProductoVersionRepo productoVersionRepo;

    @Autowired
    public ProductoService(ProductoRepo productoRepo, ProductoVersionRepo productoVersionRepo) {
        this.productoRepo = productoRepo;
        this.productoVersionRepo = productoVersionRepo;
    }


    public List<ProductoResponse> getAllProductos() {
        List<Producto> productos = (List<Producto>) productoRepo.findAll();
        if (productos.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToProductoResponseList(productos);
    }

    public List<ProductoVersionResponse> getAllVersiones(Long id){

        List<ProductoVersion> versions = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage())).getVersiones();
        if (versions.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToProductoVersionResponseList(versions);
    }

    public ProductoResponse getProductoById(Long id) {
        return Converter.convertToProductoResponse(productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage())));
    }

    public ProductoVersionResponse getProductoVersionById(Long id) {
        return Converter.convertToProductoVersionResponse(productoVersionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_VERSION_NOT_FOUND.getMessage())));
    }


    public List<TicketResponse> getAllTicketsByVersion(Long id) {
        ProductoVersion version = productoVersionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_VERSION_NOT_FOUND.getMessage()));

        if (version.getTickets().isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(version.getTickets());
    }

    public ProductoResponse crearProducto(ProductoRequest productoRequest) {
   
        Producto productoNuevo = new Producto(productoRequest);
        return Converter.convertToProductoResponse(productoRepo.save(productoNuevo));
    }

    public ProductoVersionResponse crearProductoVersion(Long id, ProductoVersionRequest productoVersionRequest) {

        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        ProductoVersion productoVersionNuevo = new ProductoVersion(productoVersionRequest);

        producto.getVersiones().add(productoVersionNuevo);
        productoVersionNuevo.setProducto(producto);

        return Converter.convertToProductoVersionResponse(productoVersionRepo.save(productoVersionNuevo));
    }

    public ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequest) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        producto.setNombre(productoRequest.getNombre());

        return Converter.convertToProductoResponse(productoRepo.save(producto));
    }

    public ProductoVersionResponse actualizarVersion(Long id, ProductoVersionRequest productoVersionRequest) {

        ProductoVersion version = productoVersionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        version.setVersion(productoVersionRequest.getVersion());

        return Converter.convertToProductoVersionResponse(productoVersionRepo.save(version));
    }


    public void quitarProducto(Long id) {
        productoRepo.deleteById(id);
    }

    public void quitarProductoVersion(Long id) {
        productoVersionRepo.deleteById(id);
    }

}
