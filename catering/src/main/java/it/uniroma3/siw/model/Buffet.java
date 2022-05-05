package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Buffet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String descrizione;
	
	@OneToOne
	private Chef chefPreparatore;
	
	@OneToMany
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
