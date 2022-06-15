package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.PiattoValidator;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private PiattoValidator piattoValidator;
	
	@GetMapping("/dishList")
	public String getBuffetList(Model model) {
		model.addAttribute("piatti", this.piattoService.findAll());
		return "dishList";
	}
	
	@GetMapping("/dish/{id}")
    public String getBuffet(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("dish", this.piattoService.findById(id));
    	return "dish";
    }
	
	@GetMapping("/addDish")
	public String addDish(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("ingredientiList", this.ingredienteService.findAll());
		return "admin/addDishForm";
	}
	
	@GetMapping("/deleteDish/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model){
		this.piattoService.deleteById(id);
		model.addAttribute("messageEN", "Dish deleted successfully");
		model.addAttribute("messageIT", "Piatto eliminato correttamente");
		return "operationSuccessful";
	}
	
	@PostMapping("/addDishForm")
	public String addDishForm(@ModelAttribute("piatto") Piatto piatto, BindingResult piattoBindingResult, Model model) {
		this.piattoValidator.validate(piatto, piattoBindingResult);
		
		if(!piattoBindingResult.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("messageEN", "Dish correctly added!");
			model.addAttribute("messageIT", "Piatto aggiunto con successo!");
			model.addAttribute("objectName", "piatto");
			return "operationSuccessful";
		}		
		return "admin/addDishForm";
	}
}
