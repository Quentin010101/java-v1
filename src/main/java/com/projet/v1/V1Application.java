package com.projet.v1;

import com.projet.v1.planner.dao.*;
import com.projet.v1.planner.enumeration.Importance;
import com.projet.v1.planner.enumeration.Progression;
import com.projet.v1.planner.repository.CompartimentRepository;
import com.projet.v1.planner.repository.TagRepository;
import com.projet.v1.planner.repository.TaskRepository;
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

import java.util.*;

@SpringBootApplication
@RestController
public class V1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}


	@Autowired
	UserRepository userRepository;
	@Autowired
	CompartimentRepository compartimentRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TaskRepository taskRepository;
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

		addTasks();
	}

	private void addTasks(){
		Compartiment c1 = new Compartiment(1,"To Do", 1);
		Compartiment c2 = new Compartiment(2,"Take Charge", 2);
		Compartiment c3 = new Compartiment(3,"Finished", 3);

		Tag ta1 = new Tag(1,"Sound");
		Tag ta2 = new Tag(2,"Sound 2");
		Tag ta3 = new Tag(3,"Sound 3");

		tagRepository.save(ta1);
		tagRepository.save(ta2);
		tagRepository.save(ta3);


		compartimentRepository.save(c1);
		compartimentRepository.save(c2);
		compartimentRepository.save(c3);

		Item i1 = new Item(1,"Apple",false);
		Item i2 = new Item(2,"Strawberries",true);
		Item i3 = new Item(3,"Bannana",true);
		Item i4 = new Item(4,"Kiwi",false);

		Commentaire co1 = new Commentaire(1,"I'm probably not going today",new Date());
		Commentaire co2 = new Commentaire(2,"Maybe later.",new Date());

		List<Item> l = new ArrayList<>();
		List<Commentaire> lc = new ArrayList<>();
		lc.add(co1);
		lc.add(co2);

		l.add(i1);
		l.add(i2);
		l.add(i3);
		l.add(i4);



		TaskDao t1 = new TaskDao(1,new Date(),new Date(),"Task to do","Remember to buy milk and bread too.",
						Progression.START.getId(), Importance.IMPORTANT.getId(),1,c1,ta1,l,lc);
		TaskDao t2 = new TaskDao(2,new Date(),new Date(),"Buy groceries","Remember to buy milk and bread too.",
						Progression.FINISH.getId(), Importance.MINIMUM.getId(),1,c3,ta3,null,null);
		TaskDao t3 = new TaskDao(3,new Date(),new Date(),"Home core","Remember to buy milk and bread too.",
						Progression.START.getId(), Importance.MOYEN.getId(),2,c1,ta2,null,null);


		taskRepository.save(t1);
		taskRepository.save(t2);
		taskRepository.save(t3);
	}
}
