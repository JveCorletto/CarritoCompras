package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.Models.Usuarios;
import com.ufg.parcial_2.DTO.APIResponses;
import com.ufg.parcial_2.Services.UsuariosService;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.security.Principal;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("API/Usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuarioService;

    @GetMapping
    public ResponseEntity<APIResponses> getAll() {
        try {
            List<Usuarios> usuarios = usuarioService.getAllUsuarios();

            if(!usuarios.isEmpty()) {
                return ResponseEntity.ok(new APIResponses(1, null, usuarios));
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "No se pudo obtener el listado de usuarios.", null));
            }
        }
        catch(Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al obtener el listado de usuarios: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponses> getById(@PathVariable Long id) {
        try {
            Usuarios usuario = usuarioService.getUsuarioById(id);

            if(usuario != null) {
                return ResponseEntity.ok(new APIResponses(1, null, usuario));
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "No se encontró el usuario", null));
            }
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.ok(new APIResponses(0, "No se encontró el usuario. " + e.getMessage(), null));
        }
    }

    @GetMapping("/getByName")
    public ResponseEntity<APIResponses> getByName(@RequestBody String usuario) {
        try {
            Usuarios usuariosList = usuarioService.findByUsuario(usuario);

            if(usuariosList != null) {
                return ResponseEntity.ok(new APIResponses(1, null, usuariosList));
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "No se pudo obtener el listado de usuarios.", null));
            }
        }
        catch(Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al obtener el usuario: " + e.getMessage(), null));
        }
    }

    @GetMapping("/getMyUser")
    @ResponseBody
    public Map<String, Object> obtenerUsuario(Principal principal) {
        String username = principal.getName();
        Usuarios usuario = usuarioService.findByUsuario(username);

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", usuario.getIdUsuario());
        userData.put("username", usuario.getNombre());

        return userData;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponses> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);

            Usuarios deletedUser = usuarioService.getUsuarioById(id);
            if (deletedUser == null) {
                return ResponseEntity.ok(new APIResponses(1, "Usuario eliminado correctamente.", null));
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "Hubo un error al eliminar el usuario.", null));
            }
        }
        catch (Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al realizar la operación: " + e.getMessage(), null));
        }
    }
}