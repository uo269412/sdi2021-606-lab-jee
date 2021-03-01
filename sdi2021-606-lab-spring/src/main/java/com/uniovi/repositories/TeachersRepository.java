package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Teacher;

public interface TeachersRepository extends CrudRepository<Teacher, Long> {

	Teacher findByDni(String dni);
	
	Page<Teacher> findAll(Pageable pageable);
	
	
}
