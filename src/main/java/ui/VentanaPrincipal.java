package ui;

import model.Maleta;
import service.ConfigService;
import service.ExcelService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {
    private final JTextField campoLargo = new JTextField(10);
    private final JTextField campoAncho = new JTextField(10);
    private final JTextField campoGrueso = new JTextField(10);
    private final JTextArea resultado = new JTextArea(10, 30);
    private final ConfigService config = new ConfigService();
    private final ExcelService excelService = new ExcelService();

    public VentanaPrincipal() {
        setTitle("Buscador de Maletas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelInputs = new JPanel(new GridLayout(4, 2));
        panelInputs.add(new JLabel("Largo (cm):"));
        panelInputs.add(campoLargo);
        panelInputs.add(new JLabel("Ancho (cm):"));
        panelInputs.add(campoAncho);
        panelInputs.add(new JLabel("Grueso (cm):"));
        panelInputs.add(campoGrueso);

        JButton btnBuscar = new JButton("Buscar maleta");
        JButton btnConfig = new JButton("Configurar sumas");
        JButton btnExcel = new JButton("Seleccionar Excel");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnBuscar);
        panelBotones.add(btnConfig);
        panelBotones.add(btnExcel);

        resultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultado);

        add(panelInputs, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarMaletas());
        btnExcel.addActionListener(e -> seleccionarArchivoExcel());
        btnConfig.addActionListener(e -> configurarSumas());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buscarMaletas() {
        try {
            double largo = Double.parseDouble(campoLargo.getText()) + config.getSumaLargo();
            double ancho = Double.parseDouble(campoAncho.getText()) + config.getSumaAncho();
            double grueso = Double.parseDouble(campoGrueso.getText()) + config.getSumaGrueso();

            List<Maleta> maletas = excelService.leerMaletas(config.getRutaExcel());

            List<Maleta> coincidencias = maletas.stream()
                .filter(m -> m.getLargo() >= largo && m.getAncho() >= ancho && m.getAlto() >= grueso)
                .collect(Collectors.toList());

            if (coincidencias.isEmpty()) {
                resultado.setText("No se encontró ninguna maleta adecuada.");
            } else {
                StringBuilder sb = new StringBuilder("Maletas encontradas:\n");
                for (Maleta m : coincidencias) {
                    sb.append("Ref: ").append(m.getRefMaleta())
                      .append(" | Proveedor: ").append(m.getProveedor()).append("\n");
                }
                resultado.setText(sb.toString());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa valores numéricos válidos.");
        }
    }

    private void seleccionarArchivoExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            config.setRutaExcel(file.getAbsolutePath());
        }
    }

    private void configurarSumas() {
        String largo = JOptionPane.showInputDialog(this, "Suma largo (mm):", config.getSumaLargo() * 10);
        String ancho = JOptionPane.showInputDialog(this, "Suma ancho (mm):", config.getSumaAncho() * 10);
        String grueso = JOptionPane.showInputDialog(this, "Suma grueso (mm):", config.getSumaGrueso() * 10);
        try {
            Properties nuevas = new Properties();
            nuevas.setProperty("suma_mm_largo", largo);
            nuevas.setProperty("suma_mm_ancho", ancho);
            nuevas.setProperty("suma_mm_grueso", grueso);
            nuevas.setProperty("ruta_excel", config.getRutaExcel());
            nuevas.store(new FileOutputStream("src/config.properties"), null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar configuración.");
        }
    }
}
