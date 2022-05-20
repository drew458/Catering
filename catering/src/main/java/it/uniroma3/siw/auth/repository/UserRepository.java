package it.uniroma3.siw.auth.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.auth.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	// TODO: fix
    public User findByEmail(String email);
}
