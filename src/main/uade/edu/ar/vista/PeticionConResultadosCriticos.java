package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.PeticionDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import main.uade.edu.ar.util.StyleUtils;
import main.uade.edu.ar.util.DateUtil;

public class PeticionConResultadosCriticos {
    private DefaultTableModel tableModel;
    private JPanel contentPane;
    private JTable practicasTable;
    private PeticionController peticionController;
    private SucursalYUsuarioController sucursalYUsuarioController;
    private PacienteController pacienteController;
    private List<PeticionDto> peticionesLista;

    public  PeticionConResultadosCriticos(PeticionController peticionController, SucursalYUsuarioController sucursalYUsuarioController, PacienteController pacienteController){
        this.peticionController = peticionController;
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.pacienteController = pacienteController;
        this.tableModel = new DefaultTableModel();
    }

    public JPanel createPanel() {
        // Crear un JPanel principal con estilo moderno
        JPanel panel = StyleUtils.createStyledPanel();
        panel.setLayout(new BorderLayout());

        // Crear un JPanel para el encabezado con estilo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título con estilo moderno
        JLabel titleLabel = StyleUtils.createTitle("⚠️ Peticiones con Resultados Críticos");
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Subtítulo informativo
        JLabel subtitleLabel = StyleUtils.createSubtitle("Lista de peticiones que requieren atención inmediata");
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Agregar el JPanel del encabezado al JPanel principal
        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear la tabla con estilo moderno
        JTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        scrollPane.getViewport().setBackground(StyleUtils.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTable createTable() {
        // Crear un modelo de tabla personalizado
        tableModel.addColumn("🆔 ID");
        tableModel.addColumn("🏥 Obra Social");
        tableModel.addColumn("👤 Paciente");
        tableModel.addColumn("🏢 Sucursal");
        tableModel.addColumn("📅 Fecha Carga");

        // Agregar datos a la tabla
        peticionesLista = peticionController.getPeticionesConResultadosCriticos();

        for (PeticionDto peticion : peticionesLista) {
            tableModel.addRow(new Object[]{
                peticion.getId(),
                peticion.getObraSocial(), 
                peticion.getPaciente().getNombre() + " " + peticion.getPaciente().getApellido(),
                "Sucursal " + peticion.getSucursal().getNumero(),
                DateUtil.formatDateWithTime(peticion.getFechaCarga())
            });
        }

        // Crear la tabla con estilo moderno
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };
        
        // Aplicar estilos modernos
        StyleUtils.styleTable(table);
        
        // Configurar ancho de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        return table;
    }


}
