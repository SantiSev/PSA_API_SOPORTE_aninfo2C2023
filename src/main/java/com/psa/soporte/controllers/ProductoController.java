package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.ProductoRequest;
import com.psa.soporte.DTO.request.ProductoVersionRequest;
import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.ProductoVersionResponse;
import com.psa.soporte.modelos.ProductoVersion;
import com.psa.soporte.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "productos")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://psa-front-nine.vercel.app/")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAllProductos() {
        return new ResponseEntity<>(productoService.getAllProductos(), HttpStatus.OK);
    }


    @GetMapping("/versiones/{id}")
    public ResponseEntity<List<ProductoVersionResponse>> getAllVersiones(@PathVariable Long id) {
        return new ResponseEntity<>(productoService.getAllVersiones(id), HttpStatus.OK);
    }


}
