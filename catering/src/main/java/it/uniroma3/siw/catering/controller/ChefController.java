package it.uniroma3.siw.catering.controller;

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
	
	@GetMapping("/addChef")
	public String addChef(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("buffetList", this.buffetService.findAll());
		return "admin/addChefForm";
	}
	
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model){
		this.chefService.deleteById(id);
		model.addAttribute("messageEN", "Chef deleted successfully");
		model.addAttribute("messageIT", "Chef eliminato correttamente");
		return "operationSuccessful";
	}
	
	@PostMapping("/addChefForm")
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
