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
    
    private class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // No permitir edición directa de ninguna celda
        }
    }

    private PacientesTodas pacientesTodas;

    private List<PacienteDto> pacienteDtoList;

    public PacientesTodas(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
        this.pacientesTodas = this;
        this.tableModel = new NonEditableTableModel();
    }

    public JPanel createPanel() {
        JPanel panel = StyleUtils.createStyledPanel();
        panel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel para título y subtítulo con BoxLayout vertical
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = StyleUtils.createTitle("👥 Pacientes");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(titleLabel);
        
        titlePanel.add(Box.createVerticalStrut(5));
        
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de pacientes del laboratorio");
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Paciente", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);
        addButton.setPreferredSize(new Dimension(230, 40));
        addButton.addActionListener(_ -> {
            AgregarPaciente agregarPaciente = new AgregarPaciente(pacienteController, this);
            agregarPaciente.setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        JTable table = createTable();
        StyleUtils.styleTable(table);
        table.setRowHeight(35); // Altura de fila más grande para los iconos
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable() {
        tableModel.addColumn("👤 Nombre");
        tableModel.addColumn("🆔 DNI");
        tableModel.addColumn("✏️ Editar");
        tableModel.addColumn("🗑️ Eliminar");

        pacienteDtoList = pacienteController.getAllPacientes();
        for (PacienteDto paciente : pacienteDtoList) {
            tableModel.addRow(new Object[]{paciente.getNombre(), paciente.getDni(), "Info", "Eliminar"});
        }

        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(2).setPreferredWidth(80); // Ancho de la columna "Editar"
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // Ancho de la columna "Eliminar"

        table.getColumn("✏️ Editar").setCellRenderer(new ButtonRenderer("✏️", StyleUtils.PRIMARY_BLUE));
        table.getColumn("🗑️ Eliminar").setCellRenderer(new ButtonRenderer("🗑️", StyleUtils.DANGER_RED));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                if (column == 2 && row < table.getRowCount()) {
                    int valorColumnaDNI = (int) tableModel.getValueAt(row, 1);

                    PacienteDto paciente = null;
                    for (PacienteDto p : pacienteDtoList) {
                        if (p.getDni() == valorColumnaDNI) {
                            paciente = p;
                            break;
                        }
                    }
                    if (paciente != null) {
                        EditarPaciente editarPaciente = new EditarPaciente(paciente, pacienteController, pacientesTodas);
                        editarPaciente.setVisible(true);
                    }
                }

                if (column == 3 && row < table.getRowCount()) {
                    int valorColumnaDNI = (int) tableModel.getValueAt(row, 1);
                    String nombrePaciente = (String) tableModel.getValueAt(row, 0);
                    
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
