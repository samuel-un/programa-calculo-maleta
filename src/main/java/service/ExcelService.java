package service;

import model.Maleta;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ExcelService {
	public List<Maleta> leerMaletas(String path) {
		List<Maleta> maletas = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(path);
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			Map<String, Integer> columnas = new HashMap<>();
			boolean encabezadosEncontrados = false;

			while (rowIterator.hasNext()) {
				Row fila = rowIterator.next();

				for (Cell celda : fila) {
					String valor = getValorCeldaComoTexto(celda).toLowerCase().replace(".", "").trim();
					if (valor.contains("largo"))
						columnas.put("largo", celda.getColumnIndex());
					else if (valor.contains("ancho"))
						columnas.put("ancho", celda.getColumnIndex());
					else if (valor.contains("grueso"))
						columnas.put("grueso", celda.getColumnIndex());
					else if (valor.contains("ref"))
						columnas.put("ref", celda.getColumnIndex());
					else if (valor.contains("proveedor"))
						columnas.put("proveedor", celda.getColumnIndex());
				}

				if (columnas.size() >= 5) {
					encabezadosEncontrados = true;
					break;
				}
			}

			if (!encabezadosEncontrados) {
				System.err.println("No se encontraron encabezados válidos en el archivo Excel.");
				return maletas;
			}

			while (rowIterator.hasNext()) {
				Row fila = rowIterator.next();
				try {
					double largo = fila.getCell(columnas.get("largo")).getNumericCellValue();
					double ancho = fila.getCell(columnas.get("ancho")).getNumericCellValue();
					double grueso = fila.getCell(columnas.get("grueso")).getNumericCellValue();
					String ref = fila.getCell(columnas.get("ref")).getStringCellValue();
					String prov = fila.getCell(columnas.get("proveedor")).getStringCellValue();
					maletas.add(new Maleta(largo, ancho, grueso, ref, prov));
				} catch (Exception ignored) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return maletas;
	}

	private String getValorCeldaComoTexto(Cell celda) {
		if (celda == null)
			return "";
		if (celda.getCellType() == CellType.STRING)
			return celda.getStringCellValue();
		if (celda.getCellType() == CellType.NUMERIC)
			return String.valueOf(celda.getNumericCellValue());
		if (celda.getCellType() == CellType.BOOLEAN)
			return String.valueOf(celda.getBooleanCellValue());
		return "";
	}
}
