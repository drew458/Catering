package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) {
		this.buffetRepository.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		this.buffetRepository.delete(buffet);
	}
	
	public Buffet findById(Long id) {
		return this.buffetRepository.findById(id).get();
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.buffetRepository.deleteById(id);
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<>();
		
		for(Buffet buffet : this.buffetRepository.findAll())
			buffets.add(buffet);
		
		return buffets;
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
	}
}
