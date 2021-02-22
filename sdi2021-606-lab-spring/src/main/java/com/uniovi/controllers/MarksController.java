package com.uniovi.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Mark;
import com.uniovi.services.MarksService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddMarkValidator;

//@RestController
@Controller
public class MarksController {

	@Autowired // Inyectar el servicio
	private MarksService marksService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddMarkValidator markValidator;

	@Autowired
	private HttpSession httpSession;

//	Antes de usar los el marksService
//	@RequestMapping("/mark/list")
//	public String getList() {
//		return "Getting List";
//	}

//	//Versión antes de usar las vistas
//	@RequestMapping("/mark/list")
//	public String getList() {
//		return marksService.getMarks().toString();
//	}

	@RequestMapping("/mark/list")
	public String getList(Model model) {
//		Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
//		if (consultedList == null) {
//			consultedList = new HashSet<Mark>();
//		}
//		model.addAttribute("consultedList", consultedList);
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list";
	}

//	@RequestMapping("/mark/add")
//	public String setMark() {
//		return "Adding Mark";
//	}

//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark() {
//		return "Adding Mark";
//	}

//	Antes de crear la clase Mark
//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark(@RequestParam String description, @RequestParam String score) {
//		return "Added: " + description + " with score: " + score;
//	}

//	Antes de usar la clase marks service
//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark(@ModelAttribute Mark mark) {
//		return "added: " + mark.getDescription() + " with score : " + mark.getScore() + " id: " + mark.getId();
//	}

//	Antes de crear las vistas
//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark(@ModelAttribute Mark mark) {
//		marksService.addMark(mark);
//		return "Ok";
//	}

//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark(@ModelAttribute Mark mark) {
//		marksService.addMark(mark);
//		return "redirect:/mark/list";
//	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(@Validated Mark mark, BindingResult result) {
		markValidator.validate(mark, result);

		if (result.hasErrors()) {
			return "mark/add";
		}
		marksService.addMark(mark);
		return "redirect:/mark/list";
	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.GET)
	public String setMark(Model model) {
		model.addAttribute("mark", new Mark());
		return "mark/add";
	}

	@RequestMapping(value = "/mark/add")
	public String getMark(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/add";
	}

//	@RequestMapping("/mark/details")
//	public String getDetail(@RequestParam Long id) {
//		return " Getting Detail: " + id;
//	}

//	Antes de usar marks services
//	@RequestMapping("/mark/details/{id}")
//	public String getDetail(@PathVariable Long id) {
//		return " Getting Detail: " + id;
//	}

//	Version antes de los nuevos html
//	@RequestMapping("/mark/details/{id}")
//	public String getDetail(@PathVariable Long id) {
//		return marksService.getMark(id).toString();
//	}

	@RequestMapping("/mark/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/details";
	}

//	// Antes de las vistas
//	@RequestMapping("/mark/delete/{id}")
//	public String deleteMark(@PathVariable Long id) {
//		marksService.deleteMark(id);
//		return "Ok";
//	}

	@RequestMapping("/mark/delete/{id}")
	public String deleteMark(@PathVariable Long id) {
		marksService.deleteMark(id);
		return "redirect:/mark/list";
	}

//	Antes de añadir a los usuarios
//	@RequestMapping(value = "/mark/add")
//	public String getMark() {
//		return "mark/add";
//	}

//	Antes de añadir a los usuarios
//	@RequestMapping(value = "/mark/edit/{id}")
//	public String getEdit(Model model, @PathVariable Long id) {
//		model.addAttribute("mark", marksService.getMark(id));
//		return "mark/edit";
//	}

	@RequestMapping(value = "/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/edit";
	}

//	Antes de añadir a los usuarios
//	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark) {
//		mark.setId(id);
//		marksService.addMark(mark);
//		return "redirect:/mark/details/" + id;
//	}

//	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark) {
//		Mark original = marksService.getMark(id);
//		// modificar solo score y description
//		original.setScore(mark.getScore());
//		original.setDescription(mark.getDescription());
//		marksService.addMark(original);
//		return "redirect:/mark/details/" + id;
//	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
	public String setEdit(@PathVariable Long id, @Validated Mark mark, BindingResult result) {
		Mark original = marksService.getMark(id);
		markValidator.validate(mark, result);
		if (result.hasErrors()) {
			return "mark/edit";
		}
		// modificar solo score y description
		original.setScore(mark.getScore());
		original.setDescription(mark.getDescription());
		marksService.addMark(original);
		return "redirect:/mark/details/" + id;
	}

	@RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		marksService.setMarkResend(true, id);
		return "redirect:/mark/list";
	}

	@RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		marksService.setMarkResend(false, id);
		return "redirect:/mark/list";
	}

	// LAB 03

	@RequestMapping("/mark/list/update")
	public String updateList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list :: tableMarks";
	}

}
