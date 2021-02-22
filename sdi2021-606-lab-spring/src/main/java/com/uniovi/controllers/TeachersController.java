package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Mark;
import com.uniovi.entities.Teacher;
import com.uniovi.services.TeachersService;
import com.uniovi.validators.AddTeacherValidator;

@Controller
public class TeachersController {

	@Autowired
	private TeachersService teachersService;

	@Autowired
	private AddTeacherValidator teacherValidator;

	@RequestMapping("/teacher/list")
	public String getList(Model model) {
		model.addAttribute("teachersList", teachersService.getTeachers());
		return "teacher/list";
	}

	@RequestMapping(value = "/teacher/add")
	public String getTeacher() {
		return "teacher/add";
	}

//	@RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
//	public String setTeacher(@ModelAttribute Teacher teacher) {
//		teachersService.addTeacher(teacher);
//		return "redirect:/teacher/list";
//	}

	@RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
	public String setTeacher(@Validated Teacher teacher, BindingResult result) {
		teacherValidator.validate(teacher, result);
		if (result.hasErrors()) {
			return "teacher/add";
		}
		teachersService.addTeacher(teacher);
		return "redirect:/teacher/list";
	}

	@RequestMapping(value = "/teacher/add", method = RequestMethod.GET)
	public String setTeacher(Model model) {
		model.addAttribute("teacher", new Teacher());
		return "teacher/add";
	}

	@RequestMapping("/teacher/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("teacher", teachersService.getTeacher(id));
		return "teacher/details";
	}

	@RequestMapping("/teacher/delete/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		teachersService.deleteTeacher(id);
		return "redirect:/teacher/list";
	}

	@RequestMapping(value = "/teacher/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("teacher", teachersService.getTeacher(id));
		return "teacher/edit";
	}

//	@RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Teacher teacher) {
//		teacher.setId(id);
//		teachersService.addTeacher(teacher);
//		return "redirect:/teacher/details/" + id;
//	}

	@RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
	public String setEdit(@PathVariable Long id, @Validated Teacher teacher, BindingResult result) {
		teacherValidator.validate(teacher, result);
		if (result.hasErrors()) {
			return "teacher/edit";
		}
		teacher.setId(id);
		teachersService.addTeacher(teacher);
		return "redirect:/teacher/details/" + id;
	}

}
