package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	ChefService chefService;
	
	@GetMapping("/prodotto/{id}")
    public String getPersona(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("prodotto", this.chefService.findById(id));
    	return "chef";
    }
}
