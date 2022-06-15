package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.IngredienteValidator;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.service.IngredienteService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private IngredienteValidator ingredienteValidator;
	
	@GetMapping("/ingredienteList")
	public String getIngredienteList(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "ingredienteList";
	}
	
	@GetMapping("/ingrediente/{id}")
    public String getIngrediente(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("ingrediente", this.ingredienteService.findById(id));
    	return "dish";
    }
	
	@GetMapping("/admin/addIngrediente")
	public String addIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "admin/addIngredienteForm";
	}
	
	@GetMapping("/admin/deleteIngrediente/{id}")
	public String deleteIngrediente(@PathVariable("id") Long id, Model model){
		this.ingredienteService.deleteById(id);
		model.addAttribute("messageEN", "Ingredient deleted successfully");
		model.addAttribute("messageIT", "Ingrediente eliminato correttamente");
		return "operationSuccessful";
	}
	
	@PostMapping("/admin/addIngredienteForm")
	public String addIngredienteForm(@ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult ingredienteBindingResult, 
			Model model) {
		this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);
		
		if(!ingredienteBindingResult.hasErrors()) {
			ingredienteService.save(ingrediente);
			model.addAttribute("messageEN", "Ingredient correctly added!");
			model.addAttribute("messageIT", "Ingrediente aggiunto con successo!");
			model.addAttribute("objectName", "ingrediente");
			return "operationSuccessful";
		}		
		return "admin/addIngredienteForm";
	}
}
