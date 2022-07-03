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

import it.uniroma3.siw.catering.controller.validator.BuffetValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	@GetMapping("/buffetList")
	public String getBuffetList(Model model) {
		model.addAttribute("buffets", this.buffetService.findAll());
		return "buffetList";
	}
	
	@GetMapping("/buffet/{id}")
    public String getBuffet(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("buffet", this.buffetService.findById(id));
    	return "buffet";
    }
	
	@GetMapping("/admin/addBuffet")
	public String addBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefList", this.chefService.findAll());
		model.addAttribute("piattoList", this.piattoService.findAll());
		return "admin/addBuffetForm";
	}
	
	@GetMapping("/admin/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, HttpServletRequest request, Model model){
		this.buffetService.deleteById(id);
		
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	@PostMapping("/admin/addBuffetForm")
	public String addBuffetForm(@ModelAttribute("buffet") Buffet buffet, BindingResult buffetBindingResult, Model model) {
		this.buffetValidator.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			buffetService.save(buffet);
			model.addAttribute("messageEN", "Buffet correctly added!");
			model.addAttribute("messageIT", "Buffet aggiunto con successo!");
			model.addAttribute("objectName", "buffet");
			return "operationSuccessful";
		}
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefList", this.chefService.findAll());
		return "admin/addBuffetForm";
	}
}
