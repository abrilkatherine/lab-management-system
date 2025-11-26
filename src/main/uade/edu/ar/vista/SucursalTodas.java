package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.SucursalDto;
import main.uade.edu.ar.util.StyleUtils;


public class SucursalTodas {

    private SucursalYUsuarioController sucursalYUsuarioController;

    private DefaultTableModel tableModel;
    
    // Modelo de tabla personalizado que no permite edición directa
    private class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // No permitir edición directa de ninguna celda
        }
    }

    private SucursalTodas sucursalTodas;

    private List<SucursalDto> sucursalDtoList;

    public SucursalTodas(SucursalYUsuarioController sucursalYUsuarioController) {
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.sucursalTodas = this;
        this.tableModel = new NonEditableTableModel();
    }

    public JPanel createPanel() {
        // Crear un JPanel con estilo moderno
        JPanel panel = StyleUtils.createStyledPanel();
        panel.setLayout(new BorderLayout());

        // Crear un JPanel para el encabezado con estilo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel para título y subtítulo con BoxLayout vertical
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Título con estilo moderno
        JLabel titleLabel = StyleUtils.createTitle("🏢 Sucursales");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(titleLabel);

        titlePanel.add(Box.createVerticalStrut(5));

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de sucursales del laboratorio");
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Botón "Agregar" con estilo moderno
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Sucursal", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);
        addButton.setPreferredSize(new Dimension(230, 40));
        addButton.addActionListener(e -> {
            AgregarSucursal agregarSucursal = new AgregarSucursal(sucursalYUsuarioController, this);
            agregarSucursal.setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear la tabla de sucursales con estilos
        JTable table = createTable();
        StyleUtils.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable() {
        // Crear un modelo de tabla personalizado que haga que todas las celdas sean no editables
        tableModel.addColumn("🏢 Número");
        tableModel.addColumn("✏️ Editar");
        tableModel.addColumn("🗑️ Eliminar");

        // Obtener la lista de sucursales mediante el controlador
        sucursalDtoList = sucursalYUsuarioController.getAllSucursales();
        for (SucursalDto sucursal : sucursalDtoList) {
            tableModel.addRow(new Object[]{sucursal.getNumero(), "Info", "Eliminar"});
        }

        // Crear la tabla y configurar el modelo
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(1).setPreferredWidth(80); // Ancho de la columna "Editar"
        table.getColumnModel().getColumn(2).setPreferredWidth(80); // Ancho de la columna "Eliminar"

        // Configurar renderer personalizado para las columnas de acciones
        table.getColumn("✏️ Editar").setCellRenderer(new ButtonRenderer("✏️", StyleUtils.PRIMARY_BLUE));
        table.getColumn("🗑️ Eliminar").setCellRenderer(new ButtonRenderer("🗑️", StyleUtils.DANGER_RED));

        // Agregar MouseListener a la tabla para detectar clics en las columnas "Editar" y "Eliminar"
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ;
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                // Verificar si se hizo clic en la columna "Editar"
                if (column == 1 && row < table.getRowCount()) {
                    // Obtener la información de la sucursal
                    int numero = (int) tableModel.getValueAt(row, 0);

                    SucursalDto sucursal = null;
                    for (SucursalDto s : sucursalDtoList) {
                        if (s.getNumero() == numero) {
                            sucursal = s;
                            break;
                        }
                    }

                    // Crear y mostrar el diálogo de editar sucursal
                    if (sucursal != null) {
                        EditarSucursal editarSucursal = new EditarSucursal(sucursal, sucursalYUsuarioController, sucursalTodas);
                        editarSucursal.setVisible(true);
                    }
                }

                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 2 && row < table.getRowCount()) {
                    int numero = (int) tableModel.getValueAt(row, 0);
                    
                    // Diálogo de confirmación con botones personalizados
                    Object[] options = {"❌ No", "✅ Sí"};
                    int confirm = JOptionPane.showOptionDialog(
                        table,
                        "¿Estás seguro de que deseas eliminar la sucursal '" + numero + "'?\n\nEsta acción no se puede deshacer.",
                        "⚠️ Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0] // "No" como opción por defecto
                    );
                    
                    // Con opciones personalizadas, 0 = "No", 1 = "Sí"
                    if (confirm == 1) { // "✅ Sí" está en la posición 1
                        SucursalDto sucursal = null;
                        for (SucursalDto s : sucursalDtoList) {
                            if (s.getNumero() == numero) {
                                sucursal = s;
                                break;
                            }
                        }
                        if (sucursal != null) {
                            try {
                                sucursalYUsuarioController.borrarSucursal(sucursal.getId());
                                tableModel.removeRow(row);
                                
                                // Mostrar mensaje de éxito
                                JOptionPane.showMessageDialog(
                                    table,
                                    "✅ Sucursal '" + numero + "' eliminada correctamente.",
                                    "Eliminación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        return table;
    }

    public void actualizarTablaSucursales() {
        tableModel.setRowCount(0);
        sucursalDtoList = null;// Elimina todas las filas existentes en el modelo
        sucursalDtoList = sucursalYUsuarioController.getAllSucursales();
        for (SucursalDto suc : sucursalDtoList) {
            tableModel.addRow(new Object[]{suc.getNumero(), "Info", "Eliminar"});
        }
    }
}

