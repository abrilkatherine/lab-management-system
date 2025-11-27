package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.dto.PracticaDto;
import main.uade.edu.ar.util.StyleUtils;

public class PracticasXPeticion extends JDialog {
    private DefaultTableModel tableModel;
    private JPanel contentPane;
    private JTable practicasTable;
    private JButton agregarButton;

    // Campo recibido pero no usado directamente - los datos se cargan desde el controlador
    @SuppressWarnings("unused")
    private List<PracticaDto> practicas;
    private int idPeticion;
    private PeticionController peticionController;

    public PracticasXPeticion(List<PracticaDto> practicas, int idPeticion, PeticionController peticionController) {
        this.practicas = practicas;
        this.idPeticion = idPeticion;
        this.peticionController = peticionController;
        initializeUI();
        cargarDatos();
    }
    
    private void initializeUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior con título y botón
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel tituloLabel = new JLabel("Prácticas");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(tituloLabel, BorderLayout.WEST);
        
        agregarButton = StyleUtils.createModernButton("+ Agregar Práctica", StyleUtils.SUCCESS_GREEN, StyleUtils.WHITE);
        agregarButton.setPreferredSize(new Dimension(230, 45));
        agregarButton.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(agregarButton, BorderLayout.EAST);
        
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Tabla con más información
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Código", "Grupo", "Horas", "Resultado", "✏️ Editar", "🗑️ Eliminar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa
            }
        };

        practicasTable = new JTable(tableModel);
        StyleUtils.styleTable(practicasTable);
        practicasTable.setRowHeight(40);
        
        // Ajustar anchos de columnas
        practicasTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        practicasTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        practicasTable.getColumnModel().getColumn(2).setPreferredWidth(90);  // Código
        practicasTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Grupo
        practicasTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Horas
        practicasTable.getColumnModel().getColumn(5).setPreferredWidth(130); // Resultado
        practicasTable.getColumnModel().getColumn(6).setPreferredWidth(90);  // Editar
        practicasTable.getColumnModel().getColumn(7).setPreferredWidth(90);  // Eliminar
        
        // Usar ButtonRenderer del proyecto
        practicasTable.getColumn("✏️ Editar").setCellRenderer(new ButtonRenderer("✏️", StyleUtils.PRIMARY_BLUE));
        practicasTable.getColumn("🗑️ Eliminar").setCellRenderer(new ButtonRenderer("🗑️", StyleUtils.DANGER_RED));
        
        // Agregar MouseListener para los clicks en los botones
        practicasTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = practicasTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / practicasTable.getRowHeight();

                if (row < practicasTable.getRowCount()) {
                    if (column == 6) { // Columna "Editar"
                        int practiceId = (int) practicasTable.getValueAt(row, 0);
                        PracticaDto selectedPractica = obtenerPracticaPorId(practiceId);

                        if (selectedPractica != null) {
                            EditarPractica editarPractica = new EditarPractica(peticionController, selectedPractica, PracticasXPeticion.this);
                            editarPractica.setAlwaysOnTop(true);
                            editarPractica.setVisible(true);
                            editarPractica.toFront();
                            editarPractica.requestFocus();
                        }
                    } else if (column == 7) { // Columna "Eliminar"
                        int practiceId = (int) practicasTable.getValueAt(row, 0);
                        String nombrePractica = (String) practicasTable.getValueAt(row, 1);
                        Object[] options = {"❌ No", "✅ Sí"};
                        int confirm = JOptionPane.showOptionDialog(
                            practicasTable,
                            "¿Estás seguro de que deseas eliminar la práctica '" + nombrePractica + "'?\n\nEsta acción no se puede deshacer.",
                            "⚠️ Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            options,
                            options[0]
                        );
                        
                        if (confirm == 1) {
                            try {
                                peticionController.borrarPractica(practiceId);
                                actualizarDatos();
                                
                                JOptionPane.showMessageDialog(
                                    practicasTable,
                                    "✅ Práctica '" + nombrePractica + "' eliminada correctamente.",
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
        
        JScrollPane scrollPane = new JScrollPane(practicasTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        agregarButton.addActionListener(e -> {
            AgregarPracticaXPeticion agregarPracticaXPeticion = new AgregarPracticaXPeticion(peticionController, idPeticion, this);
            agregarPracticaXPeticion.setAlwaysOnTop(true);
            agregarPracticaXPeticion.setVisible(true);
            agregarPracticaXPeticion.toFront();
            agregarPracticaXPeticion.requestFocus();
        });

        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }

    private PracticaDto obtenerPracticaPorId(int practiceId) {
        List<PracticaDto> practicasActualizadas = peticionController.getAllPracticasDePeticion(idPeticion);
        for (PracticaDto practica : practicasActualizadas) {
            if (practica.getId() == practiceId) {
                return practica;
            }
        }
        return null;
    }

    private void cargarDatos() {
        tableModel.setRowCount(0); // Limpiar las filas existentes

        List<PracticaDto> practicas = peticionController.getAllPracticasDePeticion(idPeticion);

        for (PracticaDto practica : practicas) {
            String estadoResultado = "Sin resultado";
            if (practica.getResultado() != null) {
                estadoResultado = practica.getResultado().getTipoResultado().toString();
            }
            
            Object[] rowData = {
                practica.getId(), 
                practica.getNombre(), 
                practica.getCodigo(),
                practica.getGrupo(),
                practica.getHorasFaltantes() + "h",
                estadoResultado,
                "Info", 
                "Eliminar"
            };
            tableModel.addRow(rowData);
        }
    }

    public void actualizarDatos() {
        tableModel.setRowCount(0); // Limpiar las filas existentes

        List<PracticaDto> practicas = peticionController.getAllPracticasDePeticion(idPeticion);

        for (PracticaDto practica : practicas) {
            String estadoResultado = "Sin resultado";
            if (practica.getResultado() != null) {
                estadoResultado = practica.getResultado().getTipoResultado().toString();
            }
            
            Object[] rowData = {
                practica.getId(), 
                practica.getNombre(), 
                practica.getCodigo(),
                practica.getGrupo(),
                practica.getHorasFaltantes() + "h",
                estadoResultado,
                "Info", 
                "Eliminar"
            };
            tableModel.addRow(rowData);
        }
    }
}
