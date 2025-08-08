package com.max.employee;

import com.max.employee.model.*;
import com.max.employee.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class InitData implements CommandLineRunner {
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public InitData(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo; this.userRepo = userRepo; this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        Role admin = roleRepo.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    return roleRepo.save(r);
                });
//        Role admin = roleRepo.findByName("ADMIN").orElseGet(() -> roleRepo.save(new Role("ADMIN")));
        if (userRepo.findByUsername("admin").isEmpty()) {
            AppUser u = new AppUser();
            u.setUsername("admin");
            u.setPassword(encoder.encode("Admin@123")); // change default password immediately
            u.setRoles(Set.of(admin));
            userRepo.save(u);
        }
    }
}
