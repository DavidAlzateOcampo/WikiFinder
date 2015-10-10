package co.edu.uniandes.wiki.dto;

import java.io.Serializable;

import co.edu.uniandes.wiki.util.TipoElemento;

public class Elemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102083472290744850L;
	String nombre;
	TipoElemento tipoElemento;

	public Elemento() {
		super();
	}

	public Elemento(String nombre, TipoElemento tipoElemento) {
		super();
		this.nombre = nombre;
		this.tipoElemento = tipoElemento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// return "Elemento [nombre=" + nombre + ", tipoElemento=" +
		// tipoElemento + "]";
		return tipoElemento + " - " + nombre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((tipoElemento == null) ? 0 : tipoElemento.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elemento other = (Elemento) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipoElemento != other.tipoElemento)
			return false;
		return true;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the tipoElemento
	 */
	public TipoElemento getTipoElemento() {
		return tipoElemento;
	}

	/**
	 * @param tipoElemento
	 *            the tipoElemento to set
	 */
	public void setTipoElemento(TipoElemento tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

}
