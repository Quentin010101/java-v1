package com.projet.v1;

import com.projet.v1.module.planner.dao.*;
import com.projet.v1.module.planner.enumeration.Importance;
import com.projet.v1.module.planner.enumeration.Progression;
import com.projet.v1.module.planner.repository.CompartimentRepository;
import com.projet.v1.module.planner.repository.TagRepository;
import com.projet.v1.module.planner.repository.TaskRepository;
import com.projet.v1.security.administration.userConfiguration.ModuleEnum;
import com.projet.v1.security.administration.userConfiguration.UserConfigurationDao;
import com.projet.v1.user.Role;
import com.projet.v1.user.User;
import com.projet.v1.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		String[] pseudo = {"quentin", "user", "demo", "Iron", "Nova","Milo"};

		int i = 0;
		for(String p : pseudo){
			User u = new User();
			u.setPseudo(p);

			if(i == 2){
				u.setAccountNonLocked(false);
			}else{
				u.setAccountNonLocked(true);
			}
			u.setPassword(passwordEncoder.encode("1234"));
			u.setDateCreation(new Date());

			switch (p){
				case "quentin" : u.setRole(Role.ADMIN); break;
				case "demo" : u.setRole(Role.DEMO); break;
				default: u.setRole(Role.USER);
			}



			UserConfigurationDao conf = new UserConfigurationDao();
			List<ModuleEnum> ll = new ArrayList<>();
			if(i == 0 || i == 1){
				ll.add(ModuleEnum.PLANNER);
				ll.add(ModuleEnum.GAMES);
			}

			conf.setModules(ll);
			u.setConfig(conf);
			userRepository.save(u);

			i++;
		}

		addTasks();
	}

	private void addTasks(){
		Compartiment c1 = new Compartiment(1,"To Do", 1);
		Compartiment c2 = new Compartiment(2,"Take Charge", 2);
		Compartiment c3 = new Compartiment(3,"Finished", 3);

		Tag ta1 = new Tag(1,"Sound");
		Tag ta2 = new Tag(2,"Sound 2");
		Tag ta3 = new Tag(3,"Sound 3");

		List<Tag> listeTag= new ArrayList<>();
		listeTag.add(ta1);
		listeTag.add(ta2);
		List<Tag> listeTag2= new ArrayList<>();
		listeTag2.add(ta2);
		listeTag2.add(ta3);
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
						Progression.START.getId(), Importance.IMPORTANT.getId(),1,c1,listeTag2,l,lc);
		TaskDao t2 = new TaskDao(2,new Date(), null,"Buy groceries","Remember to buy milk and bread too.",
						Progression.FINISH.getId(), Importance.MINIMUM.getId(),1,c3,listeTag2,null,null);
		TaskDao t3 = new TaskDao(3,new Date(),new Date(),"Home core","Remember to buy milk and bread too.",
						Progression.START.getId(), Importance.MOYEN.getId(),2,c1,listeTag,null,null);


		taskRepository.save(t1);
		taskRepository.save(t2);
		taskRepository.save(t3);
	}
}
