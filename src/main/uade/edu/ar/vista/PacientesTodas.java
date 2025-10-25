package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.util.StyleUtils;

public class PacientesTodas {

    private PacienteController pacienteController;
    private DefaultTableModel tableModel;

    private PacientesTodas pacientesTodas;

    private List<PacienteDto> pacienteDtoList;

    public PacientesTodas(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
        this.pacientesTodas = this;
        this.tableModel = new DefaultTableModel();
    }

    public JPanel createPanel() {
        // Crear un JPanel con estilo moderno
        JPanel panel = StyleUtils.createStyledPanel();
        panel.setLayout(new BorderLayout());

        // Crear un JPanel para el encabezado con estilo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título con estilo moderno
        JLabel titleLabel = StyleUtils.createTitle("👥 Pacientes");
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de pacientes del laboratorio");
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Botón "Agregar" con estilo moderno
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Paciente", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);
        addButton.addActionListener(e -> {
            AgregarPaciente agregarPaciente = new AgregarPaciente(pacienteController, this);
            agregarPaciente.setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear la tabla de pacientes con estilos
        JTable table = createTable();
        StyleUtils.styleTable(table);
        table.setRowHeight(35); // Altura de fila más grande para los iconos
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable() {
        // Configurar el modelo de tabla con iconos
        tableModel.addColumn("👤 Nombre");
        tableModel.addColumn("🆔 DNI");
        tableModel.addColumn("✏️ Editar");
        tableModel.addColumn("🗑️ Eliminar");

        // Obtener la lista de pacientes
        pacienteDtoList = pacienteController.getAllPacientes();
        for (PacienteDto paciente : pacienteDtoList) {
            tableModel.addRow(new Object[]{paciente.getNombre(), paciente.getDni(), "Info", "Eliminar"});
        }

        // Crear la tabla y configurar el modelo
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(2).setPreferredWidth(80); // Ancho de la columna "Editar"
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // Ancho de la columna "Eliminar"

        // Configurar renderer personalizado para las columnas de acciones
        table.getColumn("✏️ Editar").setCellRenderer(new ButtonRenderer("✏️", StyleUtils.PRIMARY_BLUE));
        table.getColumn("🗑️ Eliminar").setCellRenderer(new ButtonRenderer("🗑️", StyleUtils.DANGER_RED));
        
        // Agregar MouseListener a la tabla para detectar clics en las columnas "Editar" y "Eliminar"
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                // Verificar si se hizo clic en la columna "Editar"
                if (column == 2 && row < table.getRowCount()) {
                    int valorColumnaDNI = (int) tableModel.getValueAt(row, 1);

                    PacienteDto paciente = null;
                    for (PacienteDto p : pacienteDtoList) {
                        if (p.getDni() == valorColumnaDNI) {
                            paciente = p;
                            break;
                        }
                    }
                    // Crear y mostrar el diálogo de editar paciente
                    if (paciente != null) {
                        EditarPaciente editarPaciente = new EditarPaciente(paciente, pacienteController, pacientesTodas);
                        editarPaciente.setVisible(true);
                    }
                }

                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 3 && row < table.getRowCount()) {
                    int valorColumnaDNI = (int) tableModel.getValueAt(row, 1);
                    String nombrePaciente = (String) tableModel.getValueAt(row, 0);
                    
                    // Diálogo de confirmación con botones personalizados
                    Object[] options = {"❌ No", "✅ Sí"};
                    int confirm = JOptionPane.showOptionDialog(
                        table,
                        "¿Estás seguro de que deseas eliminar al paciente '" + nombrePaciente + "'?\n\nEsta acción no se puede deshacer.",
                        "⚠️ Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0] // "No" como opción por defecto
                    );
                    
                    // Con opciones personalizadas, 0 = "No", 1 = "Sí"
                    System.out.println("DEBUG: Valor de confirm = " + confirm);
                    if (confirm == 1) { // "✅ Sí" está en la posición 1
                        System.out.println("DEBUG: Usuario confirmó eliminación");
                        PacienteDto paciente = null;
                        for (PacienteDto p : pacienteDtoList) {
                            if (p.getDni() == valorColumnaDNI) {
                                paciente = p;
                                break;
                            }
                        }
                        if (paciente != null) {
                            System.out.println("DEBUG: Paciente encontrado, intentando eliminar...");
                            try{
                                pacienteController.borrarPaciente(paciente.getId());
                                tableModel.removeRow(row);
                                
                                // Mostrar mensaje de éxito
                                JOptionPane.showMessageDialog(
                                    table,
                                    "✅ Paciente '" + nombrePaciente + "' eliminado correctamente.",
                                    "Eliminación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                                System.out.println("DEBUG: Paciente eliminado exitosamente");
                            }
                            catch (Exception exception){
                                System.out.println("DEBUG: Error al eliminar paciente: " + exception.getMessage());
                                exception.printStackTrace();
                                // Mostrar mensaje más claro sobre por qué no se puede eliminar
                                String mensaje = "❌ No se puede eliminar al paciente '" + nombrePaciente + "'\n\n" +
                                              "⚠️ Razón: El paciente tiene peticiones con resultados asociados.\n" +
                                              "Para eliminar este paciente, primero debe eliminar todas sus peticiones con resultados.";
                                JOptionPane.showMessageDialog(
                                    table, 
                                    mensaje, 
                                    "⚠️ No se puede eliminar", 
                                    JOptionPane.WARNING_MESSAGE
                                );
                            }
                        } else {
                            System.out.println("DEBUG: Paciente no encontrado");
                        }
                    } else {
                        System.out.println("DEBUG: Usuario canceló eliminación (confirm = " + confirm + ")");
                    }
                }
            }
        });


        return table;
    }


    public void actualizarTablaPacientes() {
        tableModel.setRowCount(0); // Elimina todas las filas existentes en el modelo
        pacienteDtoList = pacienteController.getAllPacientes();
        for (PacienteDto paciente : pacienteDtoList) {
            tableModel.addRow(new Object[]{paciente.getNombre(),  paciente.getDni(), "Info", "Eliminar"});
        }
    }
}
