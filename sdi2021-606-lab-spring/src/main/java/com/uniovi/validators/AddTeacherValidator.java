package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Teacher;
import com.uniovi.services.TeachersService;

@Component
public class AddTeacherValidator implements Validator {
	@Autowired
	private TeachersService teachersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Teacher.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Teacher teacher = (Teacher) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
		if (teacher.getDni().length() != 9) {
			errors.rejectValue("dni", "Error.teacher.dniFormat");
		} else {
			String lastchar = teacher.getDni().substring(teacher.getDni().length() - 1);
			try {
				Integer.parseInt(lastchar);
				errors.rejectValue("dni", "Error.teacher.dniFormat");
			} catch (NumberFormatException ex) {
			}
			if (teachersService.getTeacher(teacher.getDni()) != null) {
				errors.rejectValue("dni", "Error.teacher.repeatedDni");
			}
		}
	}
}