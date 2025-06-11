package model;

public class Pieza {
	private double largo;
	private double ancho;
	private double grueso;

	public Pieza(double largo, double ancho, double grueso) {
		this.largo = largo;
		this.ancho = ancho;
		this.grueso = grueso;
	}

	public double getLargo() {
		return largo;
	}

	public double getAncho() {
		return ancho;
	}

	public double getGrueso() {
		return grueso;
	}
}