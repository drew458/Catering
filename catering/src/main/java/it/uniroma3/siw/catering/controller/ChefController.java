package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	ChefService chefService;
	
	@GetMapping("/chefList")
	public String getChefList(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chefList";
	}
	
	@GetMapping("/chef/{id}")
    public String getChef(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.findById(id));
    	return "chef";
    }
	
	@PostMapping("/addChef")
	public String addChef(Model model) {
		return "addChefForm";
	}
	
	@PostMapping("/addChefForm")
	public String addChefForm(@ModelAttribute("chef") Chef chef, BindingResult chefBindingResult) {
		//TODO: add validation
		chefService.save(chef);
		return "addSuccessful";
	}
}
