package com.psa.soporte.tools;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.enums.ExceptionMensajes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchResources {

    public static List<ClienteRequest> processClients(){

        try{
            RestTemplate restTemplate = new RestTemplate();

            // Fetch JSON data
            ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                    "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/clientes-psa/1.0.0/m/api/clientes",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<Map<String, Object>> jsonData = responseEntity.getBody();
            List<ClienteRequest> requests = new ArrayList<>();

            if (jsonData != null) {
                for (Map<String, Object> jsonEntry : jsonData) {

                    ClienteRequest request = new ClienteRequest();
                    request.setCUIT((String) jsonEntry.get("CUIT"));
                    request.setRazonSocial((String) jsonEntry.get("razon social"));
                    requests.add(request);
                }
            }
            return requests;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<ColaboradorRequest> processColaboradores(){

        try{
            RestTemplate restTemplate = new RestTemplate();

            // Fetch JSON data
            ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                    "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<Map<String, Object>> jsonData = responseEntity.getBody();
            List<ColaboradorRequest> requests = new ArrayList<>();

            if (jsonData != null) {
                for (Map<String, Object> jsonEntry : jsonData) {

                    ColaboradorRequest request = new ColaboradorRequest();
                    String nombreCompleto = (String) jsonEntry.get("Nombre") + " " + (String) jsonEntry.get("Apellido");
                    request.setNombre(nombreCompleto);
                    request.setLegajo((Integer) jsonEntry.get("legajo"));
                    requests.add(request);
                }
            }
            return requests;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
