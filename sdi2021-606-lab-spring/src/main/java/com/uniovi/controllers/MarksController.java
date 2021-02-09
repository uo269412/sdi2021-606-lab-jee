package com.uniovi.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uniovi.entities.Mark;

@RestController
public class MarksController {

	@RequestMapping("/mark/list")
	public String getList() {
		return "Getting List";
	}

//	@RequestMapping("/mark/add")
//	public String setMark() {
//		return "Adding Mark";
//	}

//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark() {
//		return "Adding Mark";
//	}

//	Before creating the mark class
//	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
//	public String setMark(@RequestParam String description, @RequestParam String score) {
//		return "Added: " + description + " with score: " + score;
//	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(@ModelAttribute Mark mark) {
		return "added: " + mark.getDescription() + " with score : " + mark.getScore() + " id: " + mark.getId();
	}

//	@RequestMapping("/mark/details")
//	public String getDetail(@RequestParam Long id) {
//		return " Getting Detail: " + id;
//	}

	@RequestMapping("/mark/details/{id}")

	public String getDetail(@PathVariable Long id) {
		return " Getting Detail: " + id;
	}
}
