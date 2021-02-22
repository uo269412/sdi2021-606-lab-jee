package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Mark;
import com.uniovi.entities.User;
import com.uniovi.repositories.MarksRepository;

@Service
public class MarksService {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MarksRepository marksRepository;

//	Esta lista desaparece para poder usar el repositorio
//	private List<Mark> marksList = new LinkedList<Mark>();

//	@PostConstruct
//	public void init() {
//		marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
//		marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
//	}

	public Page<Mark> getMarks(Pageable pageable) {
		Page<Mark> marks = marksRepository.findAll(pageable);
		return marks;
	}

//	public void setMarkResend(boolean revised, Long id) {
//		marksRepository.updateResend(revised, id);
//	}

	public void setMarkResend(boolean revised, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();
		Mark mark = marksRepository.findById(id).get();
		if (mark.getUser().getDni().equals(dni)) {
			marksRepository.updateResend(revised, id);
		}
	}

	public Page<Mark> getMarksForUser(Pageable pageable, User user) {
		Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.findAllByUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = getMarks(pageable);
		}
		return marks;
	}

//	public Mark getMark(Long id) {
//		return marksRepository.findById(id).get();
//	}

	public Mark getMark(Long id) {
		Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Mark>();
		}
		Mark obtainedmark = marksRepository.findById(id).get();
		consultedList.add(obtainedmark);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedmark;
	}

//	public void addMark(Mark mark) {
//		// Si en Id es null le asignamos el ultimo + 1 de la lista
//		if (mark.getId() == null) {
//			mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
//		}
//		marksList.add(mark);
//	}

	public void addMark(Mark mark) {
		marksRepository.save(mark);
	}

//	public void deleteMark(Long id) {
//		marksList.removeIf(mark -> mark.getId().equals(id));
//	}

	public void deleteMark(Long id) {
		marksRepository.deleteById(id);
	}

	public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchtext, User user) {
		Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
		searchtext = "%" + searchtext + "%";
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.searchByDescriptionNameAndUser(pageable, searchtext, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = marksRepository.searchByDescriptionAndName(pageable, searchtext);
		}
		return marks;
	}

}
