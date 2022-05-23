package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.auth.model.User;
import it.uniroma3.siw.catering.auth.repository.UserRepository;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class CateringController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private PiattoService piattoService;

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
	
	@GetMapping("/chef")
	public String chef(Model model) {
		model.addAttribute("chef", this.chefService.findAll());
		return "chef.html";
	}
	
	@GetMapping("/buffet")
	public String buffet(Model model) {
		model.addAttribute("buffet", this.buffetService.findAll());
		return "buffet.html";
	}
	
	@GetMapping("/piatti")
	public String piatti(Model model) {
		model.addAttribute("piatto", this.piattoService.findAll());
		return "piatti.html";
	}
}
