package com.veterinary.followup.web;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import com.veterinary.followup.service.PatientService;
import com.veterinary.followup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class MainController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String root(Authentication authentication, Model model,Principal principal) {
    	
    	UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    	if (isAdmin) {
            List<Patient> patients = patientService.getPatients();
            model.addAttribute("patients", patients);
            return "index";
        } else {

        	User currentUser = userService.findByEmail(authentication.getName());
            
            model.addAttribute("user", currentUser);
            return "index_user";
        }
	}
   
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}