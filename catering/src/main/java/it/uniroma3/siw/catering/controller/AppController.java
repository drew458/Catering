package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.auth.model.User;
import it.uniroma3.siw.auth.repository.UserRepository;

@Controller
public class AppController {

	@Autowired
	private UserRepository repository;

	@GetMapping(value = {"/", "index"})
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/login")
	public String login(User user) { 
	    return "login";
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@PostMapping("/signup_process")
	public String processSignup(User user) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword); 
	    repository.save(user);
	     
	    return "signup_success";
	}
}
