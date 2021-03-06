package it.uniroma3.siw.catering.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Buffet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String descrizione;
	private String imageUrl;
	
	/**
	 * La strategia di fetch è LAZY perche non è detto che si vuole sempre vedere l'autore del buffet
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Chef chefPreparatore;
	
	/**
	 * La strategia di fetch è EAGER perche insieme al buffet si vogliono sempre vedere i piatti che lo compongono
	 * Vengono abilitate le cascade perche l'associazione esprime una composizione
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private List<Piatto> piatti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Chef getChefPreparatore() {
		return chefPreparatore;
	}

	public void setChefPreparatore(Chef chefPreparatore) {
		this.chefPreparatore = chefPreparatore;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}

	public Buffet() {
	}

	public Buffet(Long id, String nome, String descrizione, Chef chefPreparatore, List<Piatto> piatti) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.chefPreparatore = chefPreparatore;
		this.piatti = piatti;
	}
	
	
}
