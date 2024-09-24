package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.DTO.APIResponses;
import com.ufg.parcial_2.DTO.LoginRequest;
import org.springframework.http.ResponseEntity;
import com.ufg.parcial_2.Security.PasswordUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Controller
public class LoginController {
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String value) {
        return PasswordUtils.hashPassword(value);
    }

    @PostMapping("/perform_login")
    public ResponseEntity<APIResponses> perform_login(@ModelAttribute LoginRequest loginRequest) {
        try {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getContrasenia())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new APIResponses(1, "Bienvenid@ " + loginRequest.getUsuario(), null));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new APIResponses(0, "Datos incorrectos, intente nuevamente", null));
        }
    }

//    @PostMapping("/perform_login")
//    public ResponseEntity<APIResponses> perform_login(@RequestBody LoginRequest loginRequest) {
//        try {
//            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getContrasenia())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            return ResponseEntity.ok(new APIResponses(1, "Bienvenid@ " + loginRequest.getUsuario(), null));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.ok(new APIResponses(0, "Datos incorrectos, intente nuevamente", null));
//        }
//    }
}