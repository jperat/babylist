package com.jperat.babylist;

import com.jperat.babylist.entity.Role;
import com.jperat.babylist.entity.User;
import com.jperat.babylist.exception.UserAllreadyExistException;
import com.jperat.babylist.repository.RoleRepository;
import com.jperat.babylist.repository.UserRepository;
import com.jperat.babylist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateFirstUserRunner implements CommandLineRunner {

    @Autowired
    private Environment environment;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (Boolean.parseBoolean(environment.getRequiredProperty("firstuser.createit"))) {
            this.createRoles();
            this.createUser();
        }
    }

    private void createRoles() {
        long count = roleRepository.count();
        if (count == 0) {
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(new Role("ROLE_USER"));
            roles.add(new Role("ROLE_ADMIN"));
            roleRepository.saveAll(roles);
        }
    }

    private void createUser() {
        long count = userRepository.count();
        if (count == 0) {
            User user = new User();
            user.setEmail(environment.getRequiredProperty("firstuser.email"));
            user.setFirstname(environment.getRequiredProperty("firstuser.firstname"));
            user.setLastname(environment.getRequiredProperty("firstuser.lastname"));
            user.setEnable(true);
            Role adminRole = roleRepository.findOneByRole("ROLE_ADMIN");
            user.addRole(adminRole);
            Role userRole = roleRepository.findOneByRole("ROLE_USER");
            user.addRole(userRole);
            try {
                userService.createNewUser(user);
            } catch (UserAllreadyExistException e) {
                e.printStackTrace();
            }
        }
    }



}
