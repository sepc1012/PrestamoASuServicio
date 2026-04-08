package com.prestamosasuservicio.backend.service;


import com.prestamosasuservicio.backend.entity.User;
import com.prestamosasuservicio.backend.enums.UserRole;
import com.prestamosasuservicio.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void updateProfile(String name, String newName) {
        User user = userRepository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado o inactivo"));

        user.setName(newName);
        userRepository.save(user);
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByNameAndActiveTrue(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe"));

        user.setActive(false);
        userRepository.save(user);
    }

    public void saveUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setActive(true);
        user.setRol(UserRole.USER);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
