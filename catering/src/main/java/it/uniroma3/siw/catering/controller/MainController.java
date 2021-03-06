package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class MainController {
	
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
	
	@GetMapping("/chef")
	public String chef(Model model) {
		model.addAttribute("chef", this.chefService.findAll());
		return "chef";
	}
	
	@GetMapping("/buffet")
	public String buffet(Model model) {
		model.addAttribute("buffet", this.buffetService.findAll());
		return "buffet";
	}
	
	@GetMapping("/piatti")
	public String piatti(Model model) {
		model.addAttribute("piatto", this.piattoService.findAll());
		return "piatti";
	}
	
	@GetMapping("/contactUs")
	public String contactUs(Model model) {
		return "contactPage";
	}
	
	@GetMapping("/messageSent")
	public String messageSent(Model model) {
		model.addAttribute("messageEN", "Thanks for contacting us, we will let you know asap!");
		model.addAttribute("messageIT", "Grazie per averci contattato, ti risponderemo il prima possibile!");
		return "operationSuccessful";
	}
}
