package ui;

import model.Maleta;
import service.ConfigService;
import service.ExcelService;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import service.PackingService;
import model.Pieza;

public class VentanaPrincipal extends JFrame {
	private final JTextField campoLargo = new JTextField(10);
	private final JTextField campoAncho = new JTextField(10);
	private final JTextField campoGrueso = new JTextField(10);
	private final JTextArea resultado = new JTextArea(10, 30);
	private final JTextArea piezasArea = new JTextArea(10, 30);
	private final JLabel labelRutaExcel = new JLabel("Ruta Excel no seleccionada");
	private final JLabel labelTotales = new JLabel("Totales - Largo: 0.0 | Ancho: 0.0 | Grueso: 0.0");
	private final ConfigService config = new ConfigService();
	private final ExcelService excelService = new ExcelService();
	private final List<double[]> piezas = new ArrayList<>();

	public VentanaPrincipal() {
		setTitle("Buscador de Maletas");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		limitarEntradaNumerica(campoLargo);
		limitarEntradaNumerica(campoAncho);
		limitarEntradaNumerica(campoGrueso);

		JPanel panelInputs = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelInputs.add(new JLabel("Largo (cm):"), gbc);
		gbc.gridx = 1;
		panelInputs.add(campoLargo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelInputs.add(new JLabel("Ancho (cm):"), gbc);
		gbc.gridx = 1;
		panelInputs.add(campoAncho, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelInputs.add(new JLabel("Grueso (cm):"), gbc);
		gbc.gridx = 1;
		panelInputs.add(campoGrueso, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelInputs.add(new JLabel("Archivo Excel:"), gbc);
		gbc.gridx = 1;
		panelInputs.add(labelRutaExcel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelInputs.add(new JLabel("Totales:"), gbc);
		gbc.gridx = 1;
		panelInputs.add(labelTotales, gbc);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnAnadirPieza = new JButton("Añadir pieza");
		JButton btnBuscar = new JButton("Buscar maleta");
		JButton btnConfig = new JButton("Configurar sumas");
		JButton btnExcel = new JButton("Seleccionar Excel");
		JButton btnLimpiar = new JButton("Limpiar");

		panelBotones.add(btnAnadirPieza);
		panelBotones.add(btnBuscar);
		panelBotones.add(btnConfig);
		panelBotones.add(btnExcel);
		panelBotones.add(btnLimpiar);

		JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
		panelCentro.add(new JLabel("Piezas añadidas:"), BorderLayout.NORTH);
		panelCentro.add(new JScrollPane(piezasArea), BorderLayout.CENTER);

		JPanel panelSur = new JPanel(new BorderLayout(5, 5));
		panelSur.add(new JLabel("Resultado:"), BorderLayout.NORTH);
		panelSur.add(new JScrollPane(resultado), BorderLayout.CENTER);

		add(panelInputs, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.CENTER);
		add(panelCentro, BorderLayout.EAST);
		add(panelSur, BorderLayout.SOUTH);

		btnAnadirPieza.addActionListener(e -> anadirPieza());
		btnBuscar.addActionListener(e -> buscarMaletas());
		btnExcel.addActionListener(e -> seleccionarArchivoExcel());
		btnConfig.addActionListener(e -> configurarSumas());
		btnLimpiar.addActionListener(e -> limpiarTodo());

		if (config.getRutaExcel() != null && !config.getRutaExcel().isEmpty()) {
			String nombreArchivo = new File(config.getRutaExcel()).getName();
			int idx = nombreArchivo.lastIndexOf('.');
			if (idx > 0)
				nombreArchivo = nombreArchivo.substring(0, idx);
			labelRutaExcel.setText("Excel seleccionado: " + nombreArchivo);
		}

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void limitarEntradaNumerica(JTextField campo) {
		((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if (esTextoValido(fb.getDocument().getText(0, fb.getDocument().getLength()), string)) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if (esTextoValido(fb.getDocument().getText(0, fb.getDocument().getLength()), text)) {
					super.replace(fb, offset, length, text, attrs);
				}
			}

			private boolean esTextoValido(String textoActual, String textoNuevo) {
				String textoResultante = textoActual + textoNuevo;
				return textoResultante.matches("\\d*\\.?\\d*");
			}
		});
	}

	private void anadirPieza() {
		try {
			if (piezas.size() >= 10) {
				JOptionPane.showMessageDialog(this, "Solo se pueden añadir hasta 10 piezas.");
				return;
			}
			double largo = Double.parseDouble(campoLargo.getText()) + config.getSumaLargo();
			double ancho = Double.parseDouble(campoAncho.getText()) + config.getSumaAncho();
			double grueso = Double.parseDouble(campoGrueso.getText()) + config.getSumaGrueso();
			piezas.add(new double[] { largo, ancho, grueso });

			StringBuilder sb = new StringBuilder("Piezas añadidas:\n");
			double totalLargo = 0, totalAncho = 0, totalGrueso = 0;
			for (int i = 0; i < piezas.size(); i++) {
				double[] p = piezas.get(i);
				sb.append(i + 1).append(". Largo: ").append(String.format("%.2f", p[0]))
						.append(" | Ancho: ").append(String.format("%.2f", p[1]))
						.append(" | Grueso: ").append(String.format("%.2f", p[2])).append("\n");
				totalLargo += p[0];
				totalAncho += p[1];
				totalGrueso += p[2];
			}
			piezasArea.setText(sb.toString());

			labelTotales.setText(String.format("Totales - Largo: %.2f | Ancho: %.2f | Grueso: %.2f",
					totalLargo, totalAncho, totalGrueso));

			campoLargo.setText("");
			campoAncho.setText("");
			campoGrueso.setText("");

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor ingresa valores numéricos válidos y rellena todos los campos.");
		}
	}

	private void buscarMaletas() {
		if (config.getRutaExcel() == null || config.getRutaExcel().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor selecciona un archivo Excel primero.");
			return;
		}

		if (piezas.isEmpty()) {
			resultado.setText("No se han añadido piezas.");
			return;
		}

		List<Maleta> maletas;
		try {
			maletas = excelService.leerMaletas(config.getRutaExcel());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al leer el archivo Excel: " + e.getMessage());
			return;
		}

		if (maletas == null || maletas.isEmpty()) {
			resultado.setText("El archivo Excel no contiene datos de maletas o está vacío.");
			return;
		}

		List<Pieza> piezasConvertidas = piezas.stream()
				.map(p -> new Pieza(p[0], p[1], p[2]))
				.collect(Collectors.toList());

		PackingService packingService = new PackingService();

		Maleta maletaElegida2D = null;
		List<PackingService.Cubo> distribucion2D = null;
		double mejorVolumen2D = Double.MAX_VALUE;

		for (Maleta m : maletas) {
			if (packingService.puedeEmpaquetarPlanoXY(piezasConvertidas, m)) {
				List<PackingService.Cubo> dist = packingService.obtenerDistribucionPlanoXY(piezasConvertidas, m);
				if (dist != null && m.getVolumen() < mejorVolumen2D) {
					mejorVolumen2D = m.getVolumen();
					maletaElegida2D = m;
					distribucion2D = dist;
				}
			}
		}

		Maleta maletaElegida3D = null;
		List<PackingService.Cubo> distribucion3D = null;
		double mejorVolumen3D = Double.MAX_VALUE;

		for (Maleta m : maletas) {
			if (packingService.puedeEmpaquetar(piezasConvertidas, m)) {
				List<PackingService.Cubo> dist = packingService.obtenerDistribucion(piezasConvertidas, m);
				if (dist != null && m.getVolumen() < mejorVolumen3D) {
					mejorVolumen3D = m.getVolumen();
					maletaElegida3D = m;
					distribucion3D = dist;
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		if (maletaElegida2D != null) {
			sb.append("Opción 1. Maleta óptima para una sola capa: \n");
			sb.append("Referencia: ").append(maletaElegida2D.getRefMaleta()).append("\n");
			sb.append("Proveedor: ").append(maletaElegida2D.getProveedor()).append("\n");
			sb.append(String.format("Dimensiones de la maleta: Largo=%.1f cm | Ancho=%.1f cm | Grueso=%.1f cm\n\n",
					maletaElegida2D.getLargo(), maletaElegida2D.getAncho(), maletaElegida2D.getGrueso()));
		} else {
			sb.append("No se encontró ninguna maleta que pueda empaquetar las piezas en una sola capa.\n\n");
		}

		if (maletaElegida3D != null) {
			sb.append("Opción 2. Maleta óptima para múltiples capas (3D): \n");
			sb.append("Referencia: ").append(maletaElegida3D.getRefMaleta()).append("\n");
			sb.append("Proveedor: ").append(maletaElegida3D.getProveedor()).append("\n");
			sb.append(String.format("Dimensiones de la maleta: Largo=%.1f cm | Ancho=%.1f cm | Grueso=%.1f cm\n\n",
					maletaElegida3D.getLargo(), maletaElegida3D.getAncho(), maletaElegida3D.getGrueso()));
		} else {
			sb.append("No se encontró ninguna maleta que pueda empaquetar las piezas en múltiples capas (3D).\n\n");
		}

		sb.append("----------------------------------------\n\n");

		if (maletaElegida2D != null && distribucion2D != null) {
			sb.append("Distribución de las piezas para la Opción 1:\n");
			for (int i = 0; i < distribucion2D.size(); i++) {
				PackingService.Cubo c = distribucion2D.get(i);
				sb.append(String.format(
						"Pieza %d: Posición (x=%.1f cm, y=%.1f cm, z=%.1f cm), Dimensiones (Largo=%.1f cm, Ancho=%.1f cm, Grueso=%.1f cm)\n",
						i + 1, c.x, c.y, c.z, c.largo, c.ancho, c.grueso));
			}
			sb.append("\n");
		}

		if (maletaElegida3D != null && distribucion3D != null) {
			sb.append("Distribución de las piezas para la Opción 2 (3D):\n");
			for (int i = 0; i < distribucion3D.size(); i++) {
				PackingService.Cubo c = distribucion3D.get(i);
				sb.append(String.format(
						"Pieza %d: Posición (x=%.1f cm, y=%.1f cm, z=%.1f cm), Dimensiones (Largo=%.1f cm, Ancho=%.1f cm, Grueso=%.1f cm)\n",
						i + 1, c.x, c.y, c.z, c.largo, c.ancho, c.grueso));
			}
		}

		resultado.setText(sb.toString());
		resultado.setCaretPosition(0);

	}

	private void seleccionarArchivoExcel() {
		JFileChooser chooser = new JFileChooser();
		int resultadoChooser = chooser.showOpenDialog(this);
		if (resultadoChooser == JFileChooser.APPROVE_OPTION) {
			File archivo = chooser.getSelectedFile();
			config.setRutaExcel(archivo.getAbsolutePath());

			String nombreArchivo = archivo.getName();
			int idx = nombreArchivo.lastIndexOf('.');
			if (idx > 0)
				nombreArchivo = nombreArchivo.substring(0, idx);
			labelRutaExcel.setText("Excel seleccionado: " + nombreArchivo);
		}
	}

	private void configurarSumas() {
		JTextField sumaLargo = new JTextField(String.valueOf(config.getSumaLargo()));
		JTextField sumaAncho = new JTextField(String.valueOf(config.getSumaAncho()));
		JTextField sumaGrueso = new JTextField(String.valueOf(config.getSumaGrueso()));

		Object[] message = {
				"Suma Largo (cm):", sumaLargo,
				"Suma Ancho (cm):", sumaAncho,
				"Suma Grueso (cm):", sumaGrueso
		};

		int opcion = JOptionPane.showConfirmDialog(this, message, "Configurar sumas",
				JOptionPane.OK_CANCEL_OPTION);

		if (opcion == JOptionPane.OK_OPTION) {
			try {
				double sl = Double.parseDouble(sumaLargo.getText());
				double sa = Double.parseDouble(sumaAncho.getText());
				double sg = Double.parseDouble(sumaGrueso.getText());

				if (sl < 0 || sa < 0 || sg < 0) {
					JOptionPane.showMessageDialog(this, "Las sumas no pueden ser negativas.");
					return;
				}

				config.setSumaLargo(sl);
				config.setSumaAncho(sa);
				config.setSumaGrueso(sg);

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Por favor ingresa valores numéricos válidos.");
			}
		}
	}

	private void limpiarTodo() {
		piezas.clear();
		piezasArea.setText("");
		resultado.setText("");
		labelTotales.setText("Totales - Largo: 0.0 | Ancho: 0.0 | Grueso: 0.0");
		campoLargo.setText("");
		campoAncho.setText("");
		campoGrueso.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(VentanaPrincipal::new);
	}
}
