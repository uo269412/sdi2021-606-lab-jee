package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;

//	@RequestMapping("/user/list")
//	public String getListado(Model model) {
//		model.addAttribute("usersList", usersService.getUsers());
//		return "user/list";
//	}

	@RequestMapping("/user/list")
	public String getListado(Model model, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		if (searchText != null && !searchText.isEmpty()) {
			model.addAttribute("usersList", usersService.searchUsersByNameOrSurname(searchText));
		} else {
			model.addAttribute("usersList", usersService.getUsers());
		}

		return "user/list";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "user/list :: tableUsers";
	}

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		model.addAttribute("usersList", usersService.getUsers());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		User user = usersService.getUser(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
		User original = usersService.getUser(id);
		if (user.getDni() != null) {
			original.setDni(user.getDni());
		}
		if (user.getName() != null) {
			original.setName(user.getName());
		}
		if (user.getLastName() != null) {
			original.setLastName(user.getLastName());
		}
		if (user.getPassword() != null) {
			original.setPassword(user.getPassword());
		}
		original.setId(id);
		usersService.addUser(original);
		return "redirect:/user/details/" + id;
	}

//	Version sin validar
//	@RequestMapping(value = "/signup", method = RequestMethod.POST)
//	public String signup(@ModelAttribute("user") User user, Model model) {
//		usersService.addUser(user);
//		securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
//		return "redirect:home";
//	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

//	Previous version
//	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
//	public String home(Model model) {
//		return "home";
//	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();
		User activeUser = usersService.getUserByDni(dni);
		model.addAttribute("markList", activeUser.getMarks());
		return "home";
	}
}