package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UsuariosTodos {

    private DefaultTableModel tableModel;
    
    // Modelo de tabla personalizado que no permite edición directa
    private class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // No permitir edición directa de ninguna celda
        }
    }
    private SucursalYUsuarioController sucursalYUsuarioController;
    private List<UsuarioDto> usuarioDtoList;
    private UsuariosTodos usuariosTodos;

    public UsuariosTodos(SucursalYUsuarioController sucursalYUsuarioController) {
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.usuariosTodos = this;
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
        JLabel titleLabel = StyleUtils.createTitle("👤 Usuarios");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(titleLabel);

        titlePanel.add(Box.createVerticalStrut(5));

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de usuarios del laboratorio");
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Botón "Agregar" con estilo moderno
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Usuario", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);

        // Agregar ActionListener al botón "Agregar"
        addButton.addActionListener(_ -> {
            AgregarUsuario agregarUsuario = new AgregarUsuario(sucursalYUsuarioController, this);
            agregarUsuario.setVisible(true);
        });

        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear la tabla de usuarios con estilos
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
        tableModel.addColumn("🔑 Rol");
        tableModel.addColumn("✏️ Editar");
        tableModel.addColumn("🗑️ Eliminar");

        // Obtener la lista de usuarios
        List<UsuarioDto> usuarios = sucursalYUsuarioController.getAllUsuarios();
        for (UsuarioDto usuario : usuarios) {
            tableModel.addRow(new Object[]{usuario.getNombre(), usuario.getRol(), "✏️", "🗑️"});
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
                    String nombreUsuario = (String) tableModel.getValueAt(row, 0);

                    // Obtener la lista actualizada de usuarios
                    List<UsuarioDto> usuariosActualizados = sucursalYUsuarioController.getAllUsuarios();
                    UsuarioDto usuario = null;
                    for (UsuarioDto u : usuariosActualizados) {
                        if (u.getNombre() != null && u.getNombre().equals(nombreUsuario)) {
                            usuario = u;
                            break;
                        }
                    }
                    // Crear y mostrar el diálogo de editar usuario
                    if (usuario != null) {
                        // Crear y mostrar el diálogo de editar usuario, pasando el usuario correspondiente
                        EditarUsuario editarUsuario = new EditarUsuario(usuario, sucursalYUsuarioController, usuariosTodos);
                        editarUsuario.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(
                            table,
                            "❌ No se pudo encontrar el usuario seleccionado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 3 && row < table.getRowCount()) {
                    String nombreUsuario = (String) tableModel.getValueAt(row, 0);
                    
                    // Diálogo de confirmación con botones personalizados
                    Object[] options = {"❌ No", "✅ Sí"};
                    int confirm = JOptionPane.showOptionDialog(
                        table,
                        "¿Estás seguro de que deseas eliminar al usuario '" + nombreUsuario + "'?\n\nEsta acción no se puede deshacer.",
                        "⚠️ Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0] // "No" como opción por defecto
                    );
                    
                    // Con opciones personalizadas, 0 = "No", 1 = "Sí"
                    if (confirm == 1) { // "✅ Sí" está en la posición 1
                        // Obtener la lista actualizada de usuarios
                        List<UsuarioDto> usuariosActualizados = sucursalYUsuarioController.getAllUsuarios();
                        UsuarioDto usuario = null;
                        for (UsuarioDto u : usuariosActualizados) {
                            if (u.getNombre() != null && u.getNombre().equals(nombreUsuario)) {
                                usuario = u;
                                break;
                            }
                        }
                        if (usuario != null) {
                            try{
                                sucursalYUsuarioController.eliminarUsuario(usuario.getId());
                                tableModel.removeRow(row);
                                
                                // Mostrar mensaje de éxito
                                JOptionPane.showMessageDialog(
                                    table,
                                    "✅ Usuario '" + nombreUsuario + "' eliminado correctamente.",
                                    "Eliminación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                            catch (Exception exception){
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

    public void actualizarTablaUsuarios() {
        tableModel.setRowCount(0); // Elimina todas las filas existentes en el modelo
        usuarioDtoList = sucursalYUsuarioController.getAllUsuarios();
        for (UsuarioDto usuario : usuarioDtoList) {
            tableModel.addRow(new Object[]{usuario.getNombre(),  usuario.getRol(), "✏️", "🗑️"});
        }
    }
}
