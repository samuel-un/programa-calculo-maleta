package service;

import model.Pieza;
import model.Maleta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PackingService {

	private static final double STEP = 0.5;

	public static class Cubo {
		public double x, y, z;
		public double largo, ancho, grueso;

		public Cubo(double x, double y, double z, double largo, double ancho, double grueso) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.largo = largo;
			this.ancho = ancho;
			this.grueso = grueso;
		}

		public boolean intersecta(Cubo otro) {
			return !(x + largo <= otro.x || otro.x + otro.largo <= x ||
					y + ancho <= otro.y || otro.y + otro.ancho <= y ||
					z + grueso <= otro.z || otro.z + otro.grueso <= z);
		}
	}

	private List<double[]> obtenerRotaciones(Pieza pieza) {
		List<double[]> rotaciones = new ArrayList<>();
		double l = pieza.getLargo();
		double a = pieza.getAncho();
		double g = pieza.getGrueso();

		rotaciones.add(new double[] { l, a, g });
		rotaciones.add(new double[] { l, g, a });
		rotaciones.add(new double[] { a, l, g });
		rotaciones.add(new double[] { a, g, l });
		rotaciones.add(new double[] { g, l, a });
		rotaciones.add(new double[] { g, a, l });

		return rotaciones;
	}

	private void ordenarPiezasPorVolumen(List<Pieza> piezas) {
		Collections.sort(piezas, new Comparator<Pieza>() {
			@Override
			public int compare(Pieza p1, Pieza p2) {
				double v1 = p1.getLargo() * p1.getAncho() * p1.getGrueso();
				double v2 = p2.getLargo() * p2.getAncho() * p2.getGrueso();
				return Double.compare(v2, v1);
			}
		});
	}

	public boolean puedeEmpaquetar(List<Pieza> piezas, Maleta maleta) {
		return obtenerDistribucion(piezas, maleta) != null;
	}

	public boolean puedeEmpaquetarPlanoXY(List<Pieza> piezas, Maleta maleta) {
		return obtenerDistribucionPlanoXY(piezas, maleta) != null;
	}

	public List<Cubo> obtenerDistribucion(List<Pieza> piezas, Maleta maleta) {
		List<Cubo> colocadas = new ArrayList<>();
		ordenarPiezasPorVolumen(piezas);

		for (Pieza pieza : piezas) {
			boolean colocada = false;

			for (double[] dims : obtenerRotaciones(pieza)) {
				double largo = dims[0];
				double ancho = dims[1];
				double grueso = dims[2];

				if (largo > maleta.getLargo() || ancho > maleta.getAncho() || grueso > maleta.getGrueso()) {
					continue;
				}

				for (double x = 0; x <= maleta.getLargo() - largo; x += STEP) {
					for (double y = 0; y <= maleta.getAncho() - ancho; y += STEP) {
						for (double z = 0; z <= maleta.getGrueso() - grueso; z += STEP) {
							Cubo nueva = new Cubo(x, y, z, largo, ancho, grueso);

							boolean solapa = false;
							for (Cubo c : colocadas) {
								if (nueva.intersecta(c)) {
									solapa = true;
									break;
								}
							}

							if (!solapa) {
								colocadas.add(nueva);
								colocada = true;
								break;
							}
						}
						if (colocada)
							break;
					}
					if (colocada)
						break;
				}

				if (colocada)
					break;
			}

			if (!colocada) {
				return null;
			}
		}

		return colocadas;
	}

	public List<Cubo> obtenerDistribucionPlanoXY(List<Pieza> piezas, Maleta maleta) {
		List<Cubo> colocadas = new ArrayList<>();
		ordenarPiezasPorVolumen(piezas);

		for (Pieza pieza : piezas) {
			boolean colocada = false;

			for (double[] dims : obtenerRotaciones(pieza)) {
				double largo = dims[0];
				double ancho = dims[1];
				double grueso = dims[2];

				if (largo > maleta.getLargo() || ancho > maleta.getAncho() || grueso > maleta.getGrueso()) {
					continue;
				}

				double z = 0;

				Cubo nueva = new Cubo(0, 0, z, largo, ancho, grueso);

				for (double x = 0; x <= maleta.getLargo() - largo; x += STEP) {
					for (double y = 0; y <= maleta.getAncho() - ancho; y += STEP) {
						nueva.x = x;
						nueva.y = y;

						boolean solapa = false;
						for (Cubo c : colocadas) {
							if (nueva.intersecta(c)) {
								solapa = true;
								break;
							}
						}

						if (!solapa) {
							colocadas.add(new Cubo(x, y, z, largo, ancho, grueso));
							colocada = true;
							break;
						}
					}
					if (colocada)
						break;
				}

				if (colocada)
					break;
			}

			if (!colocada) {
				return null;
			}
		}

		return colocadas;
	}
}
