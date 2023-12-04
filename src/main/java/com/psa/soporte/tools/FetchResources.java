package com.psa.soporte.tools;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.DTO.response.TareaResponse;
import com.psa.soporte.modelos.Ticket;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static List<TareaResponse> getTareas(Long ticketId){

        try{
            RestTemplate restTemplate = new RestTemplate();

            // Fetch JSON data
            ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                    "https://api-proyectos-wp7y.onrender.com/tareaTicket/ticket/" + ticketId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<Map<String, Object>> jsonData = responseEntity.getBody();

            List<TareaResponse> tareas = new ArrayList<>();

            if (jsonData != null) {
                for (Map<String, Object> jsonEntry : jsonData) {
                    Map<String, Object> tareaJson = (Map<String, Object>) jsonEntry.get("tarea");
                    TareaResponse tareaResponse = new TareaResponse();
                    tareaResponse.setTareaId((Integer) tareaJson.get("id"));
                    tareaResponse.setDescripcion((String) tareaJson.get("descripcion"));
                    tareas.add(tareaResponse);
                }
            }
            return tareas;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public static void setTicketTarea(Ticket ticket, List<Long> tareaIds){

        try{

            for (Long tareaId: tareaIds) {

                RestTemplate restTemplate = new RestTemplate();

                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("tareaId", tareaId);
                requestBody.put("ticketId", ticket.getTicketId());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

                // Fetch JSON data
                ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                        "https://api-proyectos-wp7y.onrender.com/tareaTicket",
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        }
                );

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public static List<ColaboradorRequest> processColaboradores(){

        List<ColaboradorRequest> requests = new ArrayList<>();
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


            if (jsonData != null) {
                for (Map<String, Object> jsonEntry : jsonData) {

                    ColaboradorRequest request = new ColaboradorRequest();
                    String nombreCompleto = (String) jsonEntry.get("Nombre") + " " + (String) jsonEntry.get("Apellido");
                    request.setNombre(nombreCompleto);
                    request.setLegajo((Integer) jsonEntry.get("legajo"));
                    requests.add(request);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return requests;
    }


}
