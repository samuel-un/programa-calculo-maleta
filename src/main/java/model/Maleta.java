package model;

public class Maleta {
	private double largo;
	private double ancho;
	private double alto;
	private String refMaleta;
	private String proveedor;

	public Maleta(double largo, double ancho, double alto, String refMaleta, String proveedor) {
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
		this.refMaleta = refMaleta;
		this.proveedor = proveedor;
	}

	public double getLargo() {
		return largo;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}

	public String getRefMaleta() {
		return refMaleta;
	}

	public String getProveedor() {
		return proveedor;
	}

	public double getVolumen() {
		return largo * ancho * alto;
	}

	@Override
	public String toString() {
		return "Ref: " + refMaleta + ", Proveedor: " + proveedor +
				", Largo: " + largo + " cm, Ancho: " + ancho + " cm, Alto: " + alto + " cm";
	}
}
