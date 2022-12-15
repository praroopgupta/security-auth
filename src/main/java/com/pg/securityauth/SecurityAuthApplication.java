package com.pg.securityauth;

import com.pg.securityauth.entity.Roles;
import com.pg.securityauth.entity.Users;
import com.pg.securityauth.repository.RolesRepository;
import com.pg.securityauth.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

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
		};
	}
}
