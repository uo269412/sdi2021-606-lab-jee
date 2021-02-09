package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Mark;
import com.uniovi.services.MarksService;

//@RestController
@Controller
public class MarksController {

	@Autowired // Inyectar el servicio
	private MarksService marksService;

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

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(@ModelAttribute Mark mark) {
		marksService.addMark(mark);
		return "redirect:/mark/list";
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

	@RequestMapping(value = "/mark/add")
	public String getMark() {
		return "mark/add";
	}

	@RequestMapping(value = "/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/edit";
	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark) {
		mark.setId(id);
		marksService.addMark(mark);
		return "redirect:/mark/details/" + id;
	}

}
