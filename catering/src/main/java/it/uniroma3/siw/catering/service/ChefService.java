package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		this.chefRepository.save(chef);
	}
	
	@Transactional
	public void delete(Chef chef) {
		this.chefRepository.delete(chef);
	}
	
	public Chef findById(Long id) {
		return this.chefRepository.findById(id).get();
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.chefRepository.deleteById(id);
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<>();
		
		for(Chef chef : this.chefRepository.findAll())
			chefs.add(chef);
		
		return chefs;
	}
	
	public boolean alreadyExists(Chef chef) {
		return this.chefRepository.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
}
