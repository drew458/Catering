package it.uniroma3.siw.catering.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Chef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String nazionalita;
	
	/**
	 * La strategia di fetch è LAZY perche non è detto che si vogliono sempre vedere i buffet proposti dallo chef
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private List<Buffet> buffetProposti;

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Buffet> getBuffetProposti() {
		return buffetProposti;
	}

	public void setBuffetProposti(List<Buffet> buffetProposti) {
		this.buffetProposti = buffetProposti;
	}

	public Chef() {
	}

	public Chef(Long id, String nome, String cognome, String nazionalita, List<Buffet> buffetProposti) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.buffetProposti = buffetProposti;
	}
	
	
}
