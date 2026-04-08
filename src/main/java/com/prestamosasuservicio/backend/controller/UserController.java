package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="http://localhost:3000") // frontend
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    @PutMapping("/update-profile/{name}")
    public ResponseEntity<String> updateProfile(@PathVariable String name, @RequestBody Map<String, String> body) {
        String newName = body.get("newName");
        userService.updateProfile(name, newName);
        return ResponseEntity.ok("Perfil actualizado con éxito");
    }

    @PutMapping("/change-password/{name}")
    public ResponseEntity<String> changePassword(@PathVariable String name, @RequestBody Map<String, String> body) {
        String newPassword = body.get("newPassword");
        userService.changePassword(name, newPassword);
        return ResponseEntity.ok("Contraseña actualizada con éxito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario desactivado correctamente");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");

        userService.saveUser(name, email, password);

        return ResponseEntity.ok("Usuario creado exitosamente por el administrador");
    }
}