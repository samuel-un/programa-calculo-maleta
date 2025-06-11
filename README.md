# 🧳 Buscador de Maletas por Dimensiones

Aplicación en Java con interfaz gráfica Swing para encontrar la maleta más adecuada según las dimensiones de varias piezas que el usuario introduce. Permite seleccionar un archivo Excel con la base de datos de maletas, añadir piezas con medidas, y configurar márgenes que se suman a las dimensiones.

---

## 📏 Qué hace

- Permite introducir dimensiones (largo, ancho, grueso) de varias piezas.
- Suma valores configurables a cada dimensión para márgenes o ajustes.
- Lee una base de datos de maletas desde un archivo Excel (.xlsx).
- Calcula la mejor combinación en filas y columnas para distribuir las piezas.
- Busca y muestra la maleta con el menor volumen que encaje todas las piezas.

---

## 🧠 Cómo funciona

1. El usuario introduce las medidas de cada pieza y las añade a una lista.
2. Se suma un margen configurado a cada dimensión (largo, ancho, grueso).
3. Se selecciona un archivo Excel que contiene las maletas disponibles.
4. Se calcula la distribución óptima de las piezas en filas y columnas.
5. Se filtran maletas que puedan contener esas dimensiones totales.
6. Se muestra la(s) maleta(s) con el menor volumen que cumple con las medidas.

---

## 🖥️ Interfaz de usuario

Desarrollada con **Java Swing**, incluye:

- Campos para ingresar largo, ancho y grueso de cada pieza (cm).
- Botón **"Añadir pieza"** para sumar piezas a la lista.
- Área que muestra todas las piezas añadidas con sus dimensiones.
- Botón **"Seleccionar Excel"** para cargar la base de datos de maletas.
- Botón **"Configurar sumas"** para modificar los márgenes que se añaden.
- Botón **"Buscar maleta"** para realizar el cálculo y mostrar resultados.
- Área de resultados donde se detallan las maletas encontradas.
- Botón **"Limpiar"** para reiniciar la lista y los resultados.
- Etiquetas que muestran la ruta del archivo Excel seleccionado y los totales acumulados.

---

## 📁 Estructura del proyecto

```
src/
├── model/
│   ├── Maleta.java            # Objeto maleta con dimensiones y referencia
│   └── Pieza.java             # Objeto pieza con dimensiones
├── service/
│   ├── ConfigService.java     # Gestión de configuración y ajustes
│   └── ExcelService.java      # Lectura de datos desde Excel
├── ui/
│   └── VentanaPrincipal.java  # Interfaz gráfica Java Swing
└── config.properties          # Archivo de configuración

```

---

## 🚀 Cómo ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/samuel-un/programa-calculo-maleta.git
   cd programa-calculo-maleta
   ```

2. Abre el proyecto en tu IDE Java favorito (IntelliJ, Eclipse, NetBeans, etc.)

3. Asegúrate de que el archivo `config.properties` exista en `src/` con contenido similar a:

   ```properties
   suma_largo=0.7
   suma_ancho=0.7
   suma_grueso=0.5
   ruta_excel=/ruta/a/tu/archivo/maletas.xlsx
   ```

4. Ejecuta la clase `ui.VentanaPrincipal` para abrir la interfaz gráfica.

---

## 🛠️ Tecnologías utilizadas

- Java 8+
- Java Swing para la interfaz gráfica
- Apache POI para lectura de archivos Excel
- Archivo properties para configuración persistente

---

## 📋 Licencia

Licenciado bajo MIT — libre para usar, modificar y distribuir.
# 🧳 Buscador de Maletas por Dimensiones

Aplicación en Java con interfaz gráfica Swing para encontrar la maleta más adecuada según las dimensiones de varias piezas que el usuario introduce. Permite seleccionar un archivo Excel con la base de datos de maletas, añadir piezas con medidas, y configurar márgenes que se suman a las dimensiones.

---

## 📏 Qué hace

- Permite introducir dimensiones (largo, ancho, grueso) de varias piezas.
- Suma valores configurables a cada dimensión para márgenes o ajustes.
- Lee una base de datos de maletas desde un archivo Excel (.xlsx).
- Calcula la mejor combinación en filas y columnas para distribuir las piezas.
- Busca y muestra la maleta con el menor volumen que encaje todas las piezas.

---

## 🧠 Cómo funciona

1. El usuario introduce las medidas de cada pieza y las añade a una lista.
2. Se suma un margen configurado a cada dimensión (largo, ancho, grueso).
3. Se selecciona un archivo Excel que contiene las maletas disponibles.
4. Se calcula la distribución óptima de las piezas en filas y columnas.
5. Se filtran maletas que puedan contener esas dimensiones totales.
6. Se muestra la(s) maleta(s) con el menor volumen que cumple con las medidas.

---

## 🖥️ Interfaz de usuario

Desarrollada con **Java Swing**, incluye:

- Campos para ingresar largo, ancho y grueso de cada pieza (cm).
- Botón **"Añadir pieza"** para sumar piezas a la lista.
- Área que muestra todas las piezas añadidas con sus dimensiones.
- Botón **"Seleccionar Excel"** para cargar la base de datos de maletas.
- Botón **"Configurar sumas"** para modificar los márgenes que se añaden.
- Botón **"Buscar maleta"** para realizar el cálculo y mostrar resultados.
- Área de resultados donde se detallan las maletas encontradas.
- Botón **"Limpiar"** para reiniciar la lista y los resultados.
- Etiquetas que muestran la ruta del archivo Excel seleccionado y los totales acumulados.

---

## 📁 Estructura del proyecto

```
src/
├── model/
│   ├── Maleta.java            # Objeto maleta con dimensiones y referencia
│   └── Pieza.java             # Objeto pieza con dimensiones
├── service/
│   ├── ConfigService.java     # Gestión de configuración y ajustes
│   └── ExcelService.java      # Lectura de datos desde Excel
├── ui/
│   └── VentanaPrincipal.java  # Interfaz gráfica Java Swing
└── config.properties          # Archivo de configuración

```

---

## 🚀 Cómo ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/samuel-un/programa-calculo-maleta.git
   cd programa-calculo-maleta
   ```

2. Abre el proyecto en tu IDE Java favorito (IntelliJ, Eclipse, NetBeans, etc.)

3. Asegúrate de que el archivo `config.properties` exista en `src/` con contenido similar a:

   ```properties
   suma_largo=0.7
   suma_ancho=0.7
   suma_grueso=0.5
   ruta_excel=/ruta/a/tu/archivo/maletas.xlsx
   ```

4. Ejecuta la clase `ui.VentanaPrincipal` para abrir la interfaz gráfica.

---

## 🛠️ Tecnologías utilizadas

- Java 8+
- Java Swing para la interfaz gráfica
- Apache POI para lectura de archivos Excel
- Archivo properties para configuración persistente

---

## 📋 Licencia

Licenciado bajo MIT — libre para usar, modificar y distribuir.
