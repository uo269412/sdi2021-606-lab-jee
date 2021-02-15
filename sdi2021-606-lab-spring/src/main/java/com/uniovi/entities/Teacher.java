package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Teacher {
	@Id
	@GeneratedValue
	private Long dni;
	private String nombre;
	private String apellidos;
	private String categoria;

	public Teacher() {

	}

	public Teacher(Long dNI, String nombre, String apellidos, String categoria) {
		super();
		dni = dNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.categoria = categoria;
	}

	public Long getDNI() {
		return dni;
	}

	public void setDNI(Long dNI) {
		dni = dNI;
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
		return "Teacher [DNI=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", categoria=" + categoria
				+ "]";
	}

}