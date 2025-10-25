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
        table.getColumnModel().getColumn(2).setPreferredWidth(50); // Ancho de la columna "Editar"
        table.getColumnModel().getColumn(3).setPreferredWidth(70); // Ancho de la columna "Eliminar"

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
                        // Crear y mostrar el diálogo de editar paciente, pasando el paciente correspondiente
                        EditarPaciente editarPaciente = new EditarPaciente(paciente, pacienteController, pacientesTodas);
                        editarPaciente.setVisible(true);
                    }
                }

                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 3 && row < table.getRowCount()) {
                    int confirm = JOptionPane.showConfirmDialog(table, "¿Estás seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar la fila correspondiente
                        int valorColumnaDNI = (int) tableModel.getValueAt(row, 1);


                        PacienteDto paciente = null;
                        for (PacienteDto p : pacienteDtoList) {
                            if (p.getDni() == valorColumnaDNI) {
                                paciente = p;
                                break;
                            }
                        }
                        if (paciente != null) {
                            try{
                                pacienteController.borrarPaciente(paciente.getId());
                                tableModel.removeRow(row);
                            }
                            catch (Exception exception){
                                exception.printStackTrace(); // Imprimir información de la excepción
                                // Opcional: Mostrar un mensaje de error al usuario
                                JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }
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
