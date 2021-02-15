package com.uniovi.services;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.uniovi.entities.Teacher;

@Service
public class TeachersService {

	private List<Teacher> teachersList = new LinkedList<Teacher>();

	@PostConstruct
	public void init() {
		teachersList.add(new Teacher(1L, "Pablo", "Gutierrez", "Mates"));
		teachersList.add(new Teacher(2L, "Nuria", "Puerto", "Lengua"));
	}

	public List<Teacher> getTeachers() {
		return teachersList;
	}

	public Teacher getTeacher(Long id) {
		for (Teacher teacher : teachersList) {
			if (teacher.getDNI().equals(id)) {
				return teacher;
			}
		}
		return null;
	}

	public void addTeacher(Teacher teacher) {
		if (teacher.getDNI() == null) {
			teacher.setDNI(teachersList.get(teachersList.size() - 1).getDNI() + 1);
		}
		teachersList.add(teacher);
	}

	public void deleteTeacher(Long id) {
		teachersList.removeIf(mark -> mark.getDNI().equals(id));
	}

}
