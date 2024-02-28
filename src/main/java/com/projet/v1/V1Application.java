package com.projet.v1;

import com.projet.v1.user.Role;
import com.projet.v1.user.User;
import com.projet.v1.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class V1Application implements CommandLineRunner {

	@RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}


	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		User u = new User();
		u.setPseudo("quentin");
		u.setPassword(passwordEncoder.encode("1234"));
		u.setRole(Role.ADMIN);

		User u2 = new User();
		u2.setPseudo("user");
		u2.setPassword(passwordEncoder.encode("1234"));
		u2.setRole(Role.USER);

		userRepository.save(u);
		userRepository.save(u2);
	}
}
