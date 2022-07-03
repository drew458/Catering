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
	
	@GetMapping("/piattoList")
	public String getPiattoList(Model model) {
		model.addAttribute("piatti", this.piattoService.findAll());
		return "piattoList";
	}
	
	@GetMapping("/piatto/{id}")
    public String getPiatto(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.findById(id));
    	return "piatto";
    }
	
	@GetMapping("/admin/addPiatto")
	public String addPiatto(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("ingredientiList", this.ingredienteService.findAll());
		return "admin/addPiattoForm";
	}
	
	@GetMapping("/admin/deletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, HttpServletRequest request, Model model){
		this.piattoService.deleteById(id);
		
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	@PostMapping("/admin/addPiattoForm")
	public String addPiattoForm(@ModelAttribute("piatto") Piatto piatto, BindingResult piattoBindingResult, Model model) {
		this.piattoValidator.validate(piatto, piattoBindingResult);
		
		if(!piattoBindingResult.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("messageEN", "Piatto correctly added!");
			model.addAttribute("messageIT", "Piatto aggiunto con successo!");
			model.addAttribute("objectName", "piatto");
			return "operationSuccessful";
		}		
		return "admin/addPiattoForm";
	}
}
