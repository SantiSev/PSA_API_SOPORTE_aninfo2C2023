package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProdcutoController {

    private final ProductoService productoService;

    @Autowired
    public ProdcutoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAllProductos() {
        return new ResponseEntity<>(productoService.getAllProductos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProductoById(@PathVariable Long id) {
        ProductoResponse producto = productoService.getProductoById(id);
        return producto != null ?
                new ResponseEntity<>(producto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoRequest producto) {
        ProductoResponse crearProducto = productoService.crearProducto(producto);
        return new ResponseEntity<>(crearProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest ProductoRequest) {
        ProductoResponse actualizarProducto = productoService.actualizarProducto(id, ProductoRequest);
        return actualizarProducto != null ?
                new ResponseEntity<>(actualizarProducto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarProducto(@PathVariable Long id) {
        productoService.quitarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
