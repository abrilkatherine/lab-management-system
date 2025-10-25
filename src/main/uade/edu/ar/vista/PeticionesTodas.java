package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.PeticionDto;
import main.uade.edu.ar.dto.SucursalDto;
import main.uade.edu.ar.model.Peticion;
import java.lang.reflect.Type;
import java.util.List;
import main.uade.edu.ar.dao.PeticionDao;
import main.uade.edu.ar.util.StyleUtils;

public class PeticionesTodas {

    private  PeticionController peticionController;
    private PeticionesTodas peticionesTodas;

    private DefaultTableModel tableModel;
    
    // Modelo de tabla personalizado que no permite edición directa
    private class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // No permitir edición directa de ninguna celda
        }
    }
    private SucursalYUsuarioController sucursalYUsuarioController;
    private PacienteController pacienteController;
    private List<PeticionDto> peticionesLista;

    public  PeticionesTodas(PeticionController peticionController, SucursalYUsuarioController sucursalYUsuarioController, PacienteController pacienteController){
        this.peticionController = peticionController;
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.pacienteController = pacienteController;
        this.peticionesTodas = this;
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

        // Título con estilo moderno
        JLabel titleLabel = StyleUtils.createTitle("📋 Peticiones");
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Gestión de peticiones del laboratorio");
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Botón "Agregar" con estilo moderno
        JButton addButton = StyleUtils.createModernButton("➕ Agregar Petición", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);
        addButton.addActionListener(e -> {
            AgregarPeticion agregarPeticion = new AgregarPeticion(peticionController, this);
            agregarPeticion.setVisible(true);
        });
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear la tabla de peticiones con estilos
        JTable table = createTable();
        StyleUtils.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable() {
        // Crear un modelo de tabla personalizado que haga que todas las celdas sean no editables
        tableModel.addColumn("🆔 ID");
        tableModel.addColumn("🔬 Prácticas");
        tableModel.addColumn("✏️ Editar");
        tableModel.addColumn("🗑️ Eliminar");

        // Agregar filas de ejemplo a la tabla
            peticionesLista = peticionController.getAllPeticiones();
            for (PeticionDto peticion : peticionesLista) {
                System.out.print(peticion);
                tableModel.addRow(new Object[]{peticion.getId(),"Ver", "Info", "Eliminar"});
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
                    int valorColumnaId = (int) tableModel.getValueAt(row, 0);
                    PeticionDto peticion = null;
                    for (PeticionDto p : peticionesLista) {
                        if (p.getId() == valorColumnaId) {
                            peticion = p;
                            break;
                        }
                    }
                    // Crear y mostrar el diálogo de editar sucursal
                    if (peticion != null) {
                        // Crear y mostrar el diálogo de editar sucursal, pasando la sucursal correspondiente
                        EditarPeticion editarPeticion = new EditarPeticion(peticion, peticionController, peticionesTodas, sucursalYUsuarioController, pacienteController );
                        editarPeticion.setVisible(true);
                    }
                }

                if(column == 1 && row < table.getRowCount()){
                    int valorColumnaId = (int) tableModel.getValueAt(row, 0);

                    PeticionDto peticion = null;
                    for (PeticionDto p : peticionesLista) {
                        if (p.getId() == valorColumnaId) {
                            peticion = p;
                            break;
                        }
                    }
                    // Crear y mostrar el diálogo de editar sucursal
                    if (peticion != null) {
                        // Crear y mostrar el diálogo de editar sucursal, pasando la sucursal correspondiente
                        PracticasXPeticion vistaPracticas = new PracticasXPeticion(peticion.getPracticas(), peticion.getId(), peticionController);
                        vistaPracticas.setVisible(true);
                    }
                }


                // Verificar si se hizo clic en la columna "Eliminar"
                if (column == 3 && row < table.getRowCount()) {
                    int valorColumnaId = (int) tableModel.getValueAt(row, 0);
                    
                    // Diálogo de confirmación con botones personalizados
                    Object[] options = {"❌ No", "✅ Sí"};
                    int confirm = JOptionPane.showOptionDialog(
                        table,
                        "¿Estás seguro de que deseas eliminar la petición #" + valorColumnaId + "'?\n\nEsta acción no se puede deshacer.",
                        "⚠️ Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0] // "No" como opción por defecto
                    );
                    
                    // Con opciones personalizadas, 0 = "No", 1 = "Sí"
                    if (confirm == 1) { // "✅ Sí" está en la posición 1
                        PeticionDto peticion = null;
                        for (PeticionDto p : peticionesLista) {
                            if (p.getId() == valorColumnaId) {
                                peticion = p;
                                break;
                            }
                        }
                        try{
                            peticionController.borrarPeticion(peticion.getId());
                            tableModel.removeRow(row);
                            
                            // Mostrar mensaje de éxito
                            JOptionPane.showMessageDialog(
                                table,
                                "✅ Petición #" + valorColumnaId + " eliminada correctamente.",
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
        });

        return table;
    }

    public void actualizarTablaPeticiones() {
        tableModel.setRowCount(0); // Elimina todas las filas existentes en el modelo
        peticionesLista = peticionController.getAllPeticiones();
        for (PeticionDto peticion : peticionesLista) {
            tableModel.addRow(new Object[]{peticion.getId(),"Ver", "Info", "Eliminar"});
        }
    }
}
