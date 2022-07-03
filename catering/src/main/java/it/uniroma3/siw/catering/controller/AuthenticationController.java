package it.uniroma3.siw.catering.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.CredentialsValidator;
import it.uniroma3.siw.catering.controller.validator.UserValidator;
import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.model.User;
import it.uniroma3.siw.catering.service.CredentialsService;
import it.uniroma3.siw.catering.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerForm";
	}
	
	@GetMapping("/adminRegister")
	public String showAdminRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "adminRegisterForm";
	}

	@GetMapping("/login") 
	public String showLoginForm(Model model) {		
		return "loginForm";
	}

	@GetMapping("/logout") 
	public String logout(Model model) {
		return "index";
	}
	
	@GetMapping("/resetPassword")
	public String showResetPasswordForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "resetPasswordForm";
	}
	
	@GetMapping("/changePassword")
	public String showChangePasswordForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "changePasswordForm";
	}
	
	@GetMapping("/changeName")
	public String showChangeNameForm(Model model) {
		User user = this.credentialsService.getCredentials().getUser();
		
		model.addAttribute("user", user);
		
		return "changeNameForm";
	}
	
	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		
		model.addAttribute("user", credentials.getUser());
		model.addAttribute("role", this.credentialsService.getCredentials().getRole());
		return "index";
	}
	
	@GetMapping("/oauthDefault")
	public String defaultAfterOAuthLogin(Model model, OAuth2AuthenticationToken authentication) {		
		OAuth2User oAuth2User = authentication.getPrincipal();
		Map<String,Object> attributes = oAuth2User.getAttributes();
		Credentials oauthCredentials = new Credentials();
		User oauthUser = new User();
		
		if(authentication.getAuthorizedClientRegistrationId().equals("google")) {
			Credentials userCredentials = credentialsService.getCredentials((String) attributes.get("email"));
		    
			if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
			    oauthUser.setNome((String) attributes.get("name"));
			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername((String) attributes.get("email"));
			    credentialsService.saveCredentials(oauthCredentials, false);
		    }
		}
		
		if(authentication.getAuthorizedClientRegistrationId().equals("github")) {
			Credentials userCredentials = credentialsService.getCredentials((String) attributes.get("login"));
			
		    if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
			    String userName= (String) attributes.get("login");
			    oauthUser.setNome(userName);
			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername(userName);
			    credentialsService.saveCredentials(oauthCredentials, false);
		    }
		}
		
		model.addAttribute("role", this.credentialsService.getCredentials().getRole());
		
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
			credentialsService.saveCredentials(credentials, false);
			model.addAttribute("messageEN", "Registration successful!");
			model.addAttribute("messageIT", "Registrazione effettuata con successo!");
			return "operationSuccessful";
		}
		return "registerForm";
	}
	
	@PostMapping("/adminRegister")
	public String registerAdmin(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, 
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()
				&& credentials.getMagicWord().equals(Credentials.MAGIC_WORD)) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials, true);
			model.addAttribute("messageEN", "Admin registration successful!");
			model.addAttribute("messageIT", "Registrazione Admin effettuata con successo!");
			return "operationSuccessful";
		}
		return "adminRegisterForm";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {
		// validate credentials fields
		this.credentialsValidator.validateReset(credentials, credentialsBindingResult);

		// if it hasn't invalid contents, store the Credentials into the DB
		if(!credentialsBindingResult.hasErrors()) {
			// get the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			try {
				Credentials oldCredentials = credentialsService.getCredentials(credentials.getUsername());
				credentials.setUser(oldCredentials.getUser());
				credentialsService.updatePassword(credentials, oldCredentials.getId());
				model.addAttribute("messageEN", "Password reset successful!");
				model.addAttribute("messageIT", "Reset della password effettuato correttamente!");
				return "operationSuccessful";
			}
			// user not found
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "resetPasswordForm";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, 
			Model model) {
		// validate credentials fields
		this.credentialsValidator.validateReset(credentials, credentialsBindingResult);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials oldCredentials = credentialsService.getCredentials(credentials.getUsername());

		// if it hasn't invalid contents, store the Credentials into the DB
		if(!credentialsBindingResult.hasErrors() && 
				credentialsService.encodePassword(credentials.getOldPassword()).equals(userDetails.getPassword())) {
			// get the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(oldCredentials.getUser());
			credentialsService.updatePassword(credentials, oldCredentials.getId());
			model.addAttribute("messageEN", "Password successfully changed!");
			model.addAttribute("messageIT", "Password cambiata correttamente!");
			return "operationSuccessful";
		}
		return "changePasswordForm";
	}
	
	@PostMapping("/changeName")
	public String changeName(@ModelAttribute("user") User user, BindingResult userBindingResult, 
			Model model) {
		// validate name and surname fields
		this.userValidator.validate(user, userBindingResult);

		// if it hasn't invalid contents, store the Credentials into the DB
		if(!userBindingResult.hasErrors()) {
			// get the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			userService.updateNameAndSurname(user, user.getId());
			model.addAttribute("messageEN", "Name and surname successfully changed!");
			model.addAttribute("messageIT", "Nome e cognome cambiati correttamente!");
			return "operationSuccessful";
		}
		return "changeNameForm";
	}
}
