package it.uniroma3.siw.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.BuffetValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.service.BuffetService;

@Controller
public class BuffetController {

	@Autowired
	BuffetService buffetService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	@GetMapping("/buffetList")
	public String getBuffetList(Model model) {
		model.addAttribute("buffets", this.buffetService.findAll());
		return "buffetList";
	}
	
	@GetMapping("/buffet/{id}")
    public String getBuffet(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.buffetService.findById(id));
    	return "buffet";
    }
	
	@GetMapping("/addBuffet")
	public String addBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "admin/addBuffetForm";
	}
	
	@PostMapping("/addBuffetForm")
	public String addBuffetForm(@ModelAttribute("buffet") Buffet buffet, BindingResult buffetBindingResult, Model model) {
		
		this.buffetValidator.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			model.addAttribute("messageEN", "Buffet correctly added!");
			model.addAttribute("messageIT", "Buffet aggiunto con successo!");
			buffetService.save(buffet);
			return "operationSuccessful";
		}
		
		return "admin/addBuffetForm";
	}
}
