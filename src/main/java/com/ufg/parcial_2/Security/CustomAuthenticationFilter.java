package com.ufg.parcial_2.Security;

import java.io.IOException;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(request.getInputStream());
                String username = jsonNode.get("usuario").asText();
                String password = jsonNode.get("contrasenia").asText();

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);

                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new AuthenticationServiceException("Error parsing login request body");
            }
        }

        return super.attemptAuthentication(request, response);  // Fallback to default
    }
}