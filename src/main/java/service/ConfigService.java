package service;

import java.io.*;
import java.util.Properties;

public class ConfigService {
	private static final String CONFIG_PATH = "src/config.properties";
	private final Properties props = new Properties();

	public ConfigService() {
		try (FileInputStream in = new FileInputStream(CONFIG_PATH)) {
			props.load(in);
		} catch (IOException e) {
			System.err.println("No se pudo cargar el archivo de configuración.");
		}
	}

	public double getSumaLargo() {
		return Double.parseDouble(props.getProperty("suma_mm_largo", "7")) / 10.0;
	}

	public double getSumaAncho() {
		return Double.parseDouble(props.getProperty("suma_mm_ancho", "7")) / 10.0;
	}

	public double getSumaGrueso() {
		return Double.parseDouble(props.getProperty("suma_mm_grueso", "5")) / 10.0;
	}

	public String getRutaExcel() {
		return props.getProperty("ruta_excel", "");
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
		try (FileOutputStream out = new FileOutputStream(CONFIG_PATH)) {
			props.store(out, null);
		} catch (IOException e) {
			System.err.println("No se pudo guardar la configuración.");
		}
	}
}
