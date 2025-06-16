package model;

public class Maleta {
	private double largo;
	private double ancho;
	private double grueso;
	private String refMaleta;
	private String proveedor;

	public Maleta(double largo, double ancho, double grueso, String refMaleta, String proveedor) {
		this.largo = largo;
		this.ancho = ancho;
		this.grueso = grueso;
		this.refMaleta = refMaleta;
		this.proveedor = proveedor;
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

	public String getRefMaleta() {
		return refMaleta;
	}

	public String getProveedor() {
		return proveedor;
	}

	public double getVolumen() {
		return largo * ancho * grueso;
	}

	@Override
	public String toString() {
		return "Ref: " + refMaleta + ", Proveedor: " + proveedor +
				", Largo: " + largo + " cm, Ancho: " + ancho + " cm, grueso: " + grueso + " cm";
	}
}
