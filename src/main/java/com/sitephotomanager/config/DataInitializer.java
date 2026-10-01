package com.sitephotomanager.config;

import com.sitephotomanager.entity.Role;
import com.sitephotomanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        seedUserIfNotExists("Admin User", "admin@sitephoto.com", "admin123", Role.ADMIN);
        seedUserIfNotExists("Site Engineer", "engineer@sitephoto.com", "engineer123", Role.ENGINEER);
        seedUserIfNotExists("Site Supervisor", "supervisor@sitephoto.com", "supervisor123", Role.SUPERVISOR);
        seedUserIfNotExists("Project Viewer", "viewer@sitephoto.com", "viewer123", Role.VIEWER);
    }

    private void seedUserIfNotExists(String fullName, String email, String password, Role role) {
        if (!userService.existsByEmail(email)) {
            userService.registerUser(fullName, email, password, role);
            log.info("Initialized default user: {} [{}] with password: {}", email, role, password);
        }
    }
}
