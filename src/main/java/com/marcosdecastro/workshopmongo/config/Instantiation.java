package com.marcosdecastro.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.marcosdecastro.workshopmongo.domain.Post;
import com.marcosdecastro.workshopmongo.domain.User;
import com.marcosdecastro.workshopmongo.dto.AuthorDTO;
import com.marcosdecastro.workshopmongo.dto.CommentDTO;
import com.marcosdecastro.workshopmongo.repository.PostRepository;
import com.marcosdecastro.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria,alex,bob));

		Post post1 = new Post(null, sdf.parse("10/03/2023"), "Partiu viagem", "Indo viajar!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("05/03/2023"), "Bom dia", "Acordando com chuva dnv", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("10/03/2023"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("10/03/2023"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Dia ta chuvoso mesmo!", sdf.parse("06/03/2023"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1,post2));
		
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
	}

}
