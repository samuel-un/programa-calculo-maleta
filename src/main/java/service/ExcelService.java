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
            if (rowIterator.hasNext()) {
                Row header = rowIterator.next();
                for (Cell cell : header) {
                    columnas.put(cell.getStringCellValue().toLowerCase().trim(), cell.getColumnIndex());
                }
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    double largo = row.getCell(columnas.get("largo")).getNumericCellValue();
                    double ancho = row.getCell(columnas.get("ancho")).getNumericCellValue();
                    double alto = row.getCell(columnas.get("alto")).getNumericCellValue();
                    String ref = row.getCell(columnas.get("ref_maleta")).getStringCellValue();
                    String prov = row.getCell(columnas.get("proveedor")).getStringCellValue();
                    maletas.add(new Maleta(largo, ancho, alto, ref, prov));
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maletas;
    }
}
