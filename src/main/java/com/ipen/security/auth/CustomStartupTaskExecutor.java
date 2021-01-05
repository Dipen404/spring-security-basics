package com.ipen.security.auth;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomStartupTaskExecutor {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@PostConstruct
	public void createDefaultAdmin() throws IOException {
		System.out.println("Inside create admin");

		User admin = new User("admin@gmail.com", passwordEncoder.encode("password"), "ADMIN", "ADMIN");
		Role role = roleService.findOrCreateRole("ROLE_ADMIN");
		// Set<UserRole> userRoles = Set.of(new UserRole(user, role));
		Set<UserRole> userRole = new HashSet<UserRole>();
		userRole.add(new UserRole(admin, role));
		userService.createUser(admin, userRole);
		
		User user = new User("user@gmail.com", passwordEncoder.encode("password"), "USER", "USER");
		Role roles = roleService.findOrCreateRole("ROLE_USER");
		// Set<UserRole> userRoles = Set.of(new UserRole(user, role));
		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(new UserRole(admin, roles));
		userService.createUser(user, userRoles);

		}
	}
