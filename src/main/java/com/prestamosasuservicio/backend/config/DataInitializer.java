package com.prestamosasuservicio.backend.config;

import com.prestamosasuservicio.backend.entity.User;
import com.prestamosasuservicio.backend.enums.UserRole;
import com.prestamosasuservicio.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin2@servicore.com").isEmpty()) {

            User admin = new User();
            admin.setName("Sebastian Admin");
            admin.setEmail("admin2@servicore.com");
            admin.setPassword(passwordEncoder.encode("password123"));
            admin.setRol(UserRole.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);

            System.out.println("-----------------------------------------");
            System.out.println("¡USUARIO ADMIN CREADO EXITOSAMENTE!");
            System.out.println("Email: admin2@servicore.com");
            System.out.println("Password: ");
            System.out.println("-----------------------------------------");
        } else {
            System.out.println("--> El usuario Admin ya existe en la DB.");
        }
    }
}

//clase usada con fines educativos