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
    private SucursalYUsuarioController sucursalYUsuarioController;
    private List<UsuarioDto> usuarioDtoList;
    private UsuariosTodos usuariosTodos;

    public UsuariosTodos(SucursalYUsuarioController sucursalYUsuarioController) {
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.usuariosTodos = this;
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
        JLabel titleLabel = StyleUtils.createTitle("👤 Usuarios");
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de usuarios del laboratorio");
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Botón "Agregar" con estilo moderno
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Usuario", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);

        // Agregar ActionListener al botón "Agregar"
        addButton.addActionListener(e -> {
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
            tableModel.addRow(new Object[]{usuario.getNombre(), usuario.getRol(), "Info", "Eliminar"});
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
                    String nombreUsuario = (String) tableModel.getValueAt(row, 0);


                    UsuarioDto usuario = null;
                    for (UsuarioDto u : usuarios) {
                        if (u.getNombre() == nombreUsuario) {
                            usuario = u;
                            break;
                        }
                    }
                    // Crear y mostrar el diálogo de editar usuario
                    if (usuario != null) {
                        // Crear y mostrar el diálogo de editar usuario, pasando el usuario correspondiente

                        EditarUsuario editarUsuario = new EditarUsuario(usuario, sucursalYUsuarioController, usuariosTodos);
                        editarUsuario.setVisible(true);
                    }
                }

                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 3 && row < table.getRowCount()) {
                    int confirm = JOptionPane.showConfirmDialog(table, "¿Estás seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar la fila correspondiente

                        String nombreUsuario = tableModel.getValueAt(row, 0).toString();

                        UsuarioDto usuario = null;
                        for (UsuarioDto u : usuarios) {
                            if (u.getNombre() == nombreUsuario) {
                                usuario = u;
                                break;
                            }
                        }
                        if (usuario != null) {
                            try{
                                sucursalYUsuarioController.eliminarUsuario(usuario.getId());
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

    public void actualizarTablaUsuarios() {
        tableModel.setRowCount(0); // Elimina todas las filas existentes en el modelo
        usuarioDtoList = sucursalYUsuarioController.getAllUsuarios();
        for (UsuarioDto usuario : usuarioDtoList) {
            tableModel.addRow(new Object[]{usuario.getNombre(),  usuario.getRol(), "Info", "Eliminar"});
        }
    }
}
