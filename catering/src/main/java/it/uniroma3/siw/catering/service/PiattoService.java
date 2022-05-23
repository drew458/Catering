package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) {
		this.piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		this.piattoRepository.delete(piatto);
	}
	
	public Piatto findById(Long id) {
		return this.piattoRepository.findById(id).get();
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.piattoRepository.deleteById(id);
	}
	
	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<>();
		
		for(Piatto piatto : this.piattoRepository.findAll())
			piatti.add(piatto);
		
		return piatti;
	}
	
	public boolean alreadyExists(Piatto piatto) {
		return this.piattoRepository.existsByNomeAndDescrizione(piatto.getNome(), piatto.getDescrizione());
	}
}
