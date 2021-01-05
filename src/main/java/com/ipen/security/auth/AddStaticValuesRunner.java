package com.ipen.security.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "runner.enabled", matchIfMissing = true, havingValue = "true")
public class AddStaticValuesRunner implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		User admin = new User("dipen@gmail.com", passwordEncoder.encode("password"), "ADMIN", "ADMIN");
		Role role = roleService.findOrCreateRole("ROLE_ADMIN");
		// Set<UserRole> userRoles = Set.of(new UserRole(user, role));
		Set<UserRole> userRole = new HashSet<UserRole>();
		userRole.add(new UserRole(admin, role));
		userService.createUser(admin, userRole);
		
		User user = new User("dipen@gmail.com", passwordEncoder.encode("password"), "ADMIN", "ADMIN");
		Role roles = roleService.findOrCreateRole("ROLE_USER");
		// Set<UserRole> userRoles = Set.of(new UserRole(user, role));
		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(new UserRole(admin, roles));
		userService.createUser(user, userRoles);

	}

}