package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.User;
import it.uniroma3.siw.catering.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    protected UserRepository userRepository;
	
	/**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }
    
    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }

    /***
     * Updates the Nome and Cognome attributes of the user described by the id attribute 
     * @param user the object witch cointains Nome and Cognome attributes to pass
     * @param id the id of the user to update
     */
    @Transactional
	public void updateNameAndSurname(User user, Long id) {
		User toUpdateUser = this.getUser(id);
		toUpdateUser.setNome(user.getNome());
		toUpdateUser.setCognome(user.getCognome());
		this.saveUser(toUpdateUser);		
	}
}
