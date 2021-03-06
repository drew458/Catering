package it.uniroma3.siw.catering.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.ChefValidator;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefValidator chefValidator;
	
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
	
	@GetMapping("/admin/addChef")
	public String addChef(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("buffetList", this.buffetService.findAll());
		return "admin/addChefForm";
	}
	
	@GetMapping("/admin/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, HttpServletRequest request, Model model){
		this.chefService.deleteById(id);
		
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	@PostMapping("/admin/addChefForm")
	public String addChefForm(@ModelAttribute("chef") Chef chef, BindingResult chefBindingResult, Model model) {
		this.chefValidator.validate(chef, chefBindingResult);
		
		if(!chefBindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("messageEN", "Chef correctly added!");
			model.addAttribute("messageIT", "Chef aggiunto con successo!");
			model.addAttribute("objectName", "chef");
			return "operationSuccessful";
		}		
		return "admin/addChefForm";
	}
}
