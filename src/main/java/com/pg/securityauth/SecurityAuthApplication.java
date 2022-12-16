package com.pg.securityauth;

import com.pg.securityauth.entity.Roles;
import com.pg.securityauth.entity.Users;
import com.pg.securityauth.repository.RolesRepository;
import com.pg.securityauth.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class SecurityAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityAuthApplication.class, args);
	}

	@SuppressWarnings("deprecation")
	@Bean
	public CommandLineRunner addUserAndRoleDetails(UsersRepository users, RolesRepository roles) {
		return args -> {
			// create new users
			Users user1 = new Users();
			user1.setUsername("praroop");
			user1.setPassword(new BCryptPasswordEncoder().encode("admin"));
			Users user2 = new Users();
			user2.setUsername("gupta");
			user2.setPassword(new BCryptPasswordEncoder().encode("customer"));
			users.saveAll(Arrays.asList(user1, user2));
			// create new roles
			Roles role1 = new Roles();
			role1.setRole("ROLE_CUSTOMER");
			Roles role2 = new Roles();
			role2.setRole("ROLE_ADMIN");
			Roles role3 = new Roles();
			role3.setRole("ROLE_MANAGER");
			roles.saveAll(Arrays.asList(role1, role2, role3));
			// assign roles to users
			Optional<Users> userPraroop = users.findByUsername("praroop");
			Optional<Roles> adminRole = roles.findByRole("ROLE_ADMIN");
			if(userPraroop.isPresent() && adminRole.isPresent()) {
				userPraroop.get().addRole(adminRole.get());
				users.save(userPraroop.get());
			}
			Optional<Users> userGupta = users.findByUsername("gupta");
			Optional<Roles> customerRole = roles.findByRole("ROLE_CUSTOMER");
			if(userGupta.isPresent() && customerRole.isPresent()) {
				userGupta.get().addRole(customerRole.get());
				users.save(userGupta.get());
			}
		};
	}



}
