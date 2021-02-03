package com.uniovi.sdi;

public class Comentario {
	private String usuario;
	private String detalles;

	public Comentario() {

	}

	public Comentario(String usuario, String detalles) {
		this.usuario = usuario;
		this.detalles = detalles;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
}