package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Teacher;
import com.uniovi.repositories.TeachersRepository;

@Service
public class TeachersService {

	@Autowired
	private TeachersRepository teachersRepository;

	// private List<Teacher> teachersList = new LinkedList<Teacher>();

	public List<Teacher> getTeachers() {
		List<Teacher> teachers = new ArrayList<Teacher>();
		teachersRepository.findAll().forEach(teachers::add);
		return teachers;
	}

	public Teacher getTeacher(Long id) {
		return teachersRepository.findById(id).get();
	}

	public void addTeacher(Teacher teacher) {
		teachersRepository.save(teacher);
	}

	public void deleteTeacher(Long id) {
		teachersRepository.deleteById(id);
	}

//	@PostConstruct
//	public void init() {
//		teachersList.add(new Teacher(1L, "Pablo", "Gutierrez", "Mates"));
//		teachersList.add(new Teacher(2L, "Nuria", "Puerto", "Lengua"));
//	}
//
//	public List<Teacher> getTeachers() {
//		return teachersList;
//	}
//
//	public Teacher getTeacher(Long id) {
//		for (Teacher teacher : teachersList) {
//			if (teacher.getDNI().equals(id)) {
//				return teacher;
//			}
//		}
//		return null;
//	}
//
//	public void addTeacher(Teacher teacher) {
//		if (teacher.getDNI() == null) {
//			teacher.setDNI(teachersList.get(teachersList.size() - 1).getDNI() + 1);
//		}
//		teachersList.add(teacher);
//	}
//
//	public void deleteTeacher(Long id) {
//		teachersList.removeIf(mark -> mark.getDNI().equals(id));
//	}

}
