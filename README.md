# 🧳 Luggage Finder by Dimensions

A simple Java Swing application to search for the **smallest suitable luggage** based on user-entered dimensions. Perfect for workshops, warehouses, or logistics operations that need to quickly identify fitting luggage from a catalog.

---

## 📏 What It Searches

- Finds the **smallest luggage** that satisfies:
  - Length (≥ entered value)
  - Width (≥ entered value)
  - Height (≥ entered value)
- Adds **configurable millimeter adjustments** to each entered dimension
- Loads luggage data from an **Excel file** (.xlsx) containing:
  - Dimensions
  - Reference
  - Supplier

---

## 🧠 How It Works

1. The user enters the desired dimensions (in cm).
2. The application adds fixed offsets (in mm) to each dimension:
   ```
   adjusted_length  = length  + suma_mm_largo
   adjusted_width   = width   + suma_mm_ancho
   adjusted_height  = height  + suma_mm_grueso
   ```
3. Reads luggage data from an Excel file (`.xlsx`), using the configured path.
4. Filters luggage items that meet or exceed the adjusted dimensions.
5. Selects and displays the **item with the smallest volume** that matches.

---

## 🖥️ User Interface

Built with **Java Swing**, the interface includes:

- Input fields for length, width, and height (in cm)
- A **"Search"** button to find matching luggage
- A button to **select the Excel file**
- A button to **configure adjustment values**
- A results area showing the reference, supplier, and dimensions of the selected luggage

> ⚙️ All configuration is stored in `config.properties`, and the Excel path is persistent between runs.

---

## 📁 Project Structure

```
src/
├── model/
│   └── Maleta.java             # Data model: luggage object
├── service/
│   ├── ConfigService.java      # Manages configuration and adjustments
│   └── ExcelService.java       # Reads luggage data from Excel
├── ui/
│   └── VentanaPrincipal.java   # Java Swing GUI
└── config.properties           # Stores dimension adjustments and Excel path
```

---

## 🚀 How to Run

1. **Clone the repository**:

   ```bash
   git clone https://github.com/samuel-un/programa-calculo-maleta.git
   cd programa-calculo-maleta
   ```

2. Open the project in your Java IDE (IntelliJ, Eclipse, NetBeans, etc.)

3. Make sure the `config.properties` file exists in `src/` with content like:

   ```properties
   suma_mm_largo=7
   suma_mm_ancho=7
   suma_mm_grueso=5
   ruta_excel=/path/to/your/luggage/file.xlsx
   ```

4. Run `ui.VentanaPrincipal` to launch the graphical interface.

---

## 🛠️ Technologies Used

- Java 8+
- Java Swing (GUI)
- Apache POI (for reading Excel files)
- Properties file for configuration

---

## 📋 License

Licensed under the MIT License — free to use, modify, and distribute.
