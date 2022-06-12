package it.uniroma3.siw.catering.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.RandomStringUtils;

import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}
		
    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        
        if(credentials.getUser().getCognome()==null) {
        	credentials.getUser().setCognome("OAuthDefault");
        }
        
        try {
        	credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        }
        catch(Exception e) {
        	credentials.setPassword(this.passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)));
        }
        
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public Credentials updatePassword(Credentials credentials, Long id) {
		Credentials toUpdateCredentials = credentialsRepository.findById(id).get();
    	toUpdateCredentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
    	credentialsRepository.save(toUpdateCredentials);
    	return toUpdateCredentials;
    }
    
    public String encodePassword(String password) {
    	return this.passwordEncoder.encode(password);
    }
    
    @Transactional
    public String getRoleAuthenticated() {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.getCredentials(userDetails.getUsername());
    	return credentials.getRole();
    }    
}
