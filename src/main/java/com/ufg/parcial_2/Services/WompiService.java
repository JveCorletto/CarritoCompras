package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Helpers.Wompi_Response;
import com.ufg.parcial_2.Helpers.Wompi_Compra;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class WompiService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${auth.client_id}")
    private String clientId;

    @Value("${auth.client_secret}")
    private String clientSecret;

    @Value("${auth.audience}")
    private String audience;

    @Value("${auth.audience}")
    private String compra_url;

    public String obtainAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("audience", audience);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        else {
            throw new RuntimeException("No se pudo obtener el token");
        }
    }

    public String realizarCompra(Wompi_Compra request, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        String compra_url = "https://api.wompi.sv/TransaccionCompra";
        HttpEntity<Wompi_Compra> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Wompi_Response> response = restTemplate.postForEntity(compra_url, entity, Wompi_Response.class);
        return response.getBody().getMensaje();
    }
}