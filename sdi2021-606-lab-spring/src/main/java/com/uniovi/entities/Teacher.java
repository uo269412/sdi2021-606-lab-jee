package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String apellidos;
	private String categoria;

	public Teacher() {

	}

	public Teacher(Long id, String nombre, String apellidos, String categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Teacher [DNI=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", categoria=" + categoria
				+ "]";
	}

}