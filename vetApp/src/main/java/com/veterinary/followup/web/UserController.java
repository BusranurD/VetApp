package com.veterinary.followup.web;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.veterinary.followup.web.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import com.veterinary.followup.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller

public class UserController {
    @Autowired
    private UserService userService;
	public User getUser(Long id, Authentication auth) throws ResponseStatusException {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user could not be found");
        }

        UserDetails userDetails = userService.loadUserByUsername(auth.getName());
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return user.get();
        }

        User currentUser = userService.findByEmail(auth.getName());
        if (!currentUser.equals(user.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you are not authorized to do that");
        }
        return user.get();
    }

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable Long id, Model model) throws ResponseStatusException {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user could not be found");
        }
        model.addAttribute("id", user.get().getId());
        model.addAttribute("user", user.get());
        return "userProfile";
    }
}
    