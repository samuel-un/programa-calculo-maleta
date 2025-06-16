package service;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class ConfigService {
	private final Properties props = new Properties();
	private final String externalConfigPath;

	public ConfigService() {
		try (InputStream embeddedIn = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (embeddedIn != null) {
				props.load(embeddedIn);
			}
		} catch (IOException ignored) {
		}
		externalConfigPath = obtenerRutaConfigExterna();
		cargarConfiguracionExterna();
		establecerValoresPorDefecto();
	}

	private String obtenerRutaConfigExterna() {
		String path = System.getProperty("config.path");
		return (path != null) ? path : "config.properties";
	}

	private void cargarConfiguracionExterna() {
		try (FileInputStream externalIn = new FileInputStream(externalConfigPath)) {
			props.load(externalIn);
		} catch (FileNotFoundException ignored) {
		} catch (IOException ignored) {
		}
	}

	private void establecerValoresPorDefecto() {
		props.putIfAbsent("suma_mm_largo", "7");
		props.putIfAbsent("suma_mm_ancho", "7");
		props.putIfAbsent("suma_mm_grueso", "5");
		props.putIfAbsent("ruta_excel", "");
	}

	public double getSumaLargo() {
		return Double.parseDouble(props.getProperty("suma_mm_largo")) / 10.0;
	}

	public double getSumaAncho() {
		return Double.parseDouble(props.getProperty("suma_mm_ancho")) / 10.0;
	}

	public double getSumaGrueso() {
		return Double.parseDouble(props.getProperty("suma_mm_grueso")) / 10.0;
	}

	public String getRutaExcel() {
		return props.getProperty("ruta_excel");
	}

	public void setRutaExcel(String ruta) {
		props.setProperty("ruta_excel", ruta);
		save();
	}

	public void setSumaLargo(double sumaLargo) {
		props.setProperty("suma_mm_largo", String.valueOf(sumaLargo * 10));
		save();
	}

	public void setSumaAncho(double sumaAncho) {
		props.setProperty("suma_mm_ancho", String.valueOf(sumaAncho * 10));
		save();
	}

	public void setSumaGrueso(double sumaGrueso) {
		props.setProperty("suma_mm_grueso", String.valueOf(sumaGrueso * 10));
		save();
	}

	private void save() {
		try (FileOutputStream out = new FileOutputStream(externalConfigPath)) {
			props.store(out, "Configuración de la aplicación");
			System.out.println("Configuración guardada en: " + new File(externalConfigPath).getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Error guardando configuración: " + e.getMessage());
			JOptionPane.showMessageDialog(null,
					"No se pudo guardar la configuración en:\n" + externalConfigPath,
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
