package it.uniroma3.siw.catering.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import it.uniroma3.siw.catering.controller.validator.CredentialsValidator;
import it.uniroma3.siw.catering.controller.validator.UserValidator;
import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.model.User;
import it.uniroma3.siw.catering.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUser";
	}

	@GetMapping("/login") 
	public String showLoginForm (Model model) {		
		return "loginForm";
	}

	@GetMapping("/logout") 
	public String logout(Model model) {
		return "index";
	}
	
	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials.getUser());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/home";
		}
		return "index";
	}

	@GetMapping("/OauthDefault")
	public String defaultAfterOAuthLogin(Model model, OAuth2AuthenticationToken authentication) {		
		// credentials are null, user got logged in via OAuth 2
		// get the client corresponding to the current user token
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		
		// send a request to the client's user info endpoint and retrieve the userAttributes Map
		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		
		if (!userInfoEndpointUri.isEmpty()) {
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		    HttpEntity<String> entity = new HttpEntity<>("", headers);
		    ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		    Map userAttributes = response.getBody();
		    Credentials userCredentials = credentialsService.getCredentials((String) userAttributes.get("email"));
		    if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();
			    oauthUser.setNome((String) userAttributes.get("name"));
			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername((String) userAttributes.get("email"));
			    credentialsService.saveCredentials(oauthCredentials);
			    model.addAttribute("user", oauthUser);
		    }			    
		}			
		return "index";
	}		

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccessful";
		}
		return "registerUser";
	}
}
