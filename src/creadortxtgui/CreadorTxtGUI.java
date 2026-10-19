package creadortxtgui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author maryse
 */
public class CreadorTxtGUI extends JFrame {

    private JTextArea textArea;
    private JButton btnGuardar;

    public CreadorTxtGUI() {
        // Configuración de la ventana principal
        setTitle("Creador de Archivos TXT - Interfaz Gráfica");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Componentes de la interfaz
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        btnGuardar = new JButton("Guardar como .txt");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        // Diseño (Layout)
        setLayout(new BorderLayout(10, 10));

        JLabel etiqueta = new JLabel(" Escribe el contenido de tu archivo a continuación:", JLabel.LEFT);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));

        add(etiqueta, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);

        // Espaciado interno para que no se vea amontonado
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Acción del botón guardar
        btnGuardar.addActionListener(e -> guardarArchivo());
    }

    private void guardarArchivo() {
        // Selector de archivos del sistema
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo de texto");

        // Sugerir un nombre por defecto
        fileChooser.setSelectedFile(new File("/home/maryse/NetBeansProjects/CreadorTxtGUI/src/creadortxtgui/documento.txt"));

        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Asegurarse de que termine en .txt
            String rutaStr = archivoSeleccionado.getAbsolutePath();
            if (!rutaStr.toLowerCase().endsWith(".txt")) {
                archivoSeleccionado = new File(rutaStr + ".txt");
            }

            try {
                // Obtener el texto de la caja y escribirlo usando NIO.2
                String contenido = textArea.getText();
                Files.writeString(archivoSeleccionado.toPath(), contenido);

                // Mensaje de éxito
                JOptionPane.showMessageDialog(
                        this,
                        "¡Archivo guardado con éxito!\nUbicación: " + archivoSeleccionado.getAbsolutePath(),
                        "Guardado exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IOException ex) {
                // Mensaje de error si falla
                JOptionPane.showMessageDialog(
                        this,
                        "Ocurrió un error al guardar el archivo:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica de forma segura en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new CreadorTxtGUI().setVisible(true);
        });
    }
}
