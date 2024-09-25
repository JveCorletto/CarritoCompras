package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.Models.Usuarios;
import com.ufg.parcial_2.DTO.APIResponses;
import com.ufg.parcial_2.DTO.LoginRequest;
import com.ufg.parcial_2.DTO.RegisterRequest;
import org.springframework.http.ResponseEntity;
import com.ufg.parcial_2.Security.PasswordUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.ufg.parcial_2.Services.UsuariosService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Controller
public class LoginController {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UsuariosService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String value) {
        return PasswordUtils.hashPassword(value);
    }

    @PostMapping("/perform_login")
    public RedirectView performLogin(@ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes) {
        try {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getContrasenia())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            redirectAttributes.addFlashAttribute("success", "Bienvenid@ " + loginRequest.getUsuario());
            return new RedirectView("/home");

        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("error", "Datos incorrectos, intente nuevamente.");
            return new RedirectView("/login");
        }
    }

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes) {
        try {
            if (registerRequest.getRegisterPassword().equals(registerRequest.getRegisterRepeatPassword())) {
                if (registerRequest != null) {
                    if (usuarioService.findByUsuario(registerRequest.getRegisterUsername()) == null) {
                        Usuarios usuario = new Usuarios();
                        usuario.setNombre(registerRequest.getRegisterUsername());
                        usuario.setUsuario(registerRequest.getRegisterUsername());
                        usuario.setContrasenia(registerRequest.getRegisterPassword());

                        usuario = usuarioService.saveUsuario(usuario);

                        if (usuario.getIdUsuario() > 0) {
                            redirectAttributes.addFlashAttribute("success", "Usuario creado correctamente.");
                            return new RedirectView("/login");
                        } else {
                            redirectAttributes.addFlashAttribute("error", "Hubo un error al crear el usuario.");
                            return new RedirectView("/login#pills-register");
                        }
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Usuario ya existente.");
                        return new RedirectView("/login#pills-register");
                    }
                } else {
                    redirectAttributes.addFlashAttribute("error", "Debe de brindar un usuario válido a guardar.");
                    return new RedirectView("/login#pills-register");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
                return new RedirectView("/login#pills-register");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la operación: " + e.getMessage());
            return new RedirectView("/login#pills-register");
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