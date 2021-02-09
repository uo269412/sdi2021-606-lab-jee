package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Mark;
import com.uniovi.repositories.MarksRepository;

@Service
public class MarksService {

	@Autowired
	private MarksRepository marksRepository;

//	Esta lista desaparece para poder usar el repositorio
//	private List<Mark> marksList = new LinkedList<Mark>();

//	@PostConstruct
//	public void init() {
//		marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
//		marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
//	}

	public List<Mark> getMarks() {
		List<Mark> marks = new ArrayList<Mark>();
		marksRepository.findAll().forEach(marks::add);
		return marks;
	}

	public Mark getMark(Long id) {
		return marksRepository.findById(id).get();
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

}
