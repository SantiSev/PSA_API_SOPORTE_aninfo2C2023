package com.psa.soporte;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "PSA - API Soporte",
				version = "1.0.0",
				description = "API para el sector de Soporte del sistema de gestion de PSA"

		)
)
public class SoporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoporteApplication.class, args);
	}
		//swagger UI: http://localhost:8080/swagger-ui/index.html
}
