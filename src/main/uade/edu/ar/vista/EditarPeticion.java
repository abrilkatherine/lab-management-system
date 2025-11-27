package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.dto.PeticionDto;
import main.uade.edu.ar.dto.SucursalDto;
import main.uade.edu.ar.util.StyleUtils;

public class EditarPeticion extends JDialog {
    private JPanel contentPane;
    private JTextField obraSocial;
    private JTextField fechaCarga;
    private JTextField fechaEntrega;
    private JComboBox<String> sucursalComboBox;
    private JComboBox<String> pacienteComboBox;
    private JButton guardarButton;
    private JButton cancelarButton;
    
    private PeticionDto peticionDto;
    private List<SucursalDto> sucursales;
    private SucursalYUsuarioController sucursalYUsuarioController;
    private PacienteController pacienteController;
    private List<PacienteDto> pacientes;
    private PeticionController peticionController;
    private PeticionesTodas peticionesTodas;

    public EditarPeticion(PeticionDto peticionDto, PeticionController peticionController, PeticionesTodas peticionesTodas, SucursalYUsuarioController sucursalYUsuarioController, PacienteController pacienteController) {
        this.peticionesTodas = peticionesTodas;
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.pacienteController = pacienteController;
        this.peticionDto = peticionDto;
        this.peticionController = peticionController;
        cargarSucursales();
        cargarPacientes();
        initializeUI();
        setListeners();
        cargarDatos();
    }

    private void cargarSucursales() {
        sucursales = sucursalYUsuarioController.getAllSucursales();
    }

    private void cargarPacientes() {
        pacientes = pacienteController.getAllPacientes();
    }

    private void initializeUI() {
        setTitle("Editar Petición");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("✏️ Editar Petición");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(StyleUtils.DARK_TEXT);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(StyleUtils.WHITE);

        JPanel datosPeticionPanel = createSection("Datos de la Petición");
        datosPeticionPanel.add(createFormField("Obra Social:", obraSocial = createStyledTextField()));
        datosPeticionPanel.add(Box.createVerticalStrut(12));
        
        // ComboBox de sucursal
        JPanel sucursalPanel = new JPanel(new BorderLayout(10, 5));
        sucursalPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        sucursalPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel sucursalLabel = new JLabel("Sucursal:");
        sucursalLabel.setFont(StyleUtils.TEXT_FONT);
        sucursalLabel.setForeground(StyleUtils.DARK_TEXT);
        sucursalPanel.add(sucursalLabel, BorderLayout.NORTH);
        
        sucursalComboBox = new JComboBox<>();
        for (SucursalDto sucursal : sucursales) {
            String displayText = sucursal.getNumero() + " _ " + sucursal.getId();
            sucursalComboBox.addItem(displayText);
        }
        sucursalComboBox.setFont(StyleUtils.TEXT_FONT);
        sucursalComboBox.setPreferredSize(new Dimension(0, 42));
        sucursalComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        sucursalComboBox.setBackground(StyleUtils.WHITE);
        sucursalComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        sucursalPanel.add(sucursalComboBox, BorderLayout.CENTER);
        datosPeticionPanel.add(sucursalPanel);
        datosPeticionPanel.add(Box.createVerticalStrut(12));
        
        // ComboBox de paciente
        JPanel pacientePanel = new JPanel(new BorderLayout(10, 5));
        pacientePanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        pacientePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel pacienteLabel = new JLabel("Paciente:");
        pacienteLabel.setFont(StyleUtils.TEXT_FONT);
        pacienteLabel.setForeground(StyleUtils.DARK_TEXT);
        pacientePanel.add(pacienteLabel, BorderLayout.NORTH);
        
        pacienteComboBox = new JComboBox<>();
        for (PacienteDto paciente : pacientes) {
            String displayText = paciente.getNombre() + " - " + paciente.getApellido();
            pacienteComboBox.addItem(displayText);
        }
        pacienteComboBox.setFont(StyleUtils.TEXT_FONT);
        pacienteComboBox.setPreferredSize(new Dimension(0, 42));
        pacienteComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        pacienteComboBox.setBackground(StyleUtils.WHITE);
        pacienteComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        pacientePanel.add(pacienteComboBox, BorderLayout.CENTER);
        datosPeticionPanel.add(pacientePanel);
        datosPeticionPanel.add(Box.createVerticalStrut(12));
        
        // Campos de fecha en la misma línea
        JPanel fechasPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        fechasPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        fechasPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        fechasPanel.add(createFormFieldWithHint("Fecha Carga:", 
            fechaCarga = createStyledTextField(), "dd/MM/yyyy (ej: 20/04/2025)"));
        fechasPanel.add(createFormFieldWithHint("Fecha Entrega:", 
            fechaEntrega = createStyledTextField(), "dd/MM/yyyy (ej: 21/04/2025)"));
        datosPeticionPanel.add(fechasPanel);
        
        formPanel.add(datosPeticionPanel);
        contentPane.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(StyleUtils.WHITE);
        
        cancelarButton = StyleUtils.createModernButton("Cancelar", StyleUtils.SECONDARY_GRAY, StyleUtils.WHITE);
        cancelarButton.setPreferredSize(new Dimension(150, 45));
        buttonPanel.add(cancelarButton);
        
        guardarButton = StyleUtils.createModernButton("Guardar", StyleUtils.PRIMARY_BLUE, StyleUtils.WHITE);
        guardarButton.setPreferredSize(new Dimension(150, 45));
        buttonPanel.add(guardarButton);
        
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(guardarButton);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), 
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(750, 650);
        setLocationRelativeTo(null);
    }

    private JPanel createSection(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.LIGHT_GRAY, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setForeground(StyleUtils.PRIMARY_BLUE);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        section.add(sectionTitle);
        section.add(Box.createVerticalStrut(15));
        
        return section;
    }

    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout(10, 5));
        fieldPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        fieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel label = new JLabel(labelText);
        label.setFont(StyleUtils.TEXT_FONT);
        label.setForeground(StyleUtils.DARK_TEXT);
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);
        
        return fieldPanel;
    }

    private JPanel createFormFieldWithHint(String labelText, JTextField textField, String hint) {
        JPanel fieldPanel = new JPanel(new BorderLayout(10, 3));
        fieldPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        
        JLabel label = new JLabel(labelText);
        label.setFont(StyleUtils.TEXT_FONT);
        label.setForeground(StyleUtils.DARK_TEXT);
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);
        
        JLabel hintLabel = new JLabel("💡 " + hint);
        hintLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        hintLabel.setForeground(StyleUtils.MEDIUM_GRAY);
        fieldPanel.add(hintLabel, BorderLayout.SOUTH);
        
        return fieldPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(StyleUtils.TEXT_FONT);
        field.setForeground(StyleUtils.DARK_TEXT);
        field.setBackground(StyleUtils.WHITE);
        field.setPreferredSize(new Dimension(0, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleUtils.PRIMARY_BLUE, 2),
                    new EmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        return field;
    }

    private void setListeners() {
        guardarButton.addActionListener(e -> onGuardar());
        cancelarButton.addActionListener(e -> onCancel());
    }

    private void cargarDatos() {
        obraSocial.setText(peticionDto.getObraSocial());
        
        // Cargar fechas solo si no son null
        if (peticionDto.getFechaCarga() != null) {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            fechaCarga.setText(displayFormat.format(peticionDto.getFechaCarga()));
            fechaCarga.setForeground(StyleUtils.DARK_TEXT);
        }
        
        if (peticionDto.getFechaEntrega() != null) {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            fechaEntrega.setText(displayFormat.format(peticionDto.getFechaEntrega()));
            fechaEntrega.setForeground(StyleUtils.DARK_TEXT);
        }
        
        sucursalComboBox.setSelectedItem(getDisplayText(peticionDto.getSucursal()));
        pacienteComboBox.setSelectedItem(getDisplayTextPacientes(peticionDto.getPaciente()));
    }

    private String getDisplayText(SucursalDto sucursalDto) {
        return sucursalDto.getNumero() + " - " + sucursalDto.getId();
    }
    
    private String getDisplayTextPacientes(PacienteDto pacienteDto) {
        return pacienteDto.getNombre() + " - " + pacienteDto.getApellido();
    }
    
    private void onGuardar() {
        String obra = obraSocial.getText();
        String sucursalText = (String) sucursalComboBox.getSelectedItem();
        SucursalDto sucursal = findSucursalByDisplayText(sucursalText);
        String pacienteText = (String) pacienteComboBox.getSelectedItem();
        PacienteDto paciente = findPacienteByDisplayText(pacienteText);
        String fechaC = fechaCarga.getText();
        String fechaI = fechaEntrega.getText();

        // Intentar múltiples formatos de fecha
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH),
            new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH),
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        };
        
        Date fechaCargaDate = null;
        Date fechaEntregaDate = null;

        // Parsear fecha de carga
        if (!fechaC.isEmpty()) {
            for (SimpleDateFormat format : dateFormats) {
                try {
                    fechaCargaDate = format.parse(fechaC);
                    break;
                } catch (ParseException e) {
                    // Continuar con el siguiente formato
                }
            }
            
            if (fechaCargaDate == null) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error: Formato de fecha de carga inválido.\n\n" +
                    "📅 Formatos aceptados:\n" +
                    "• dd/MM/yyyy (ej: 20/04/2020)\n" +
                    "• MM/dd/yyyy (ej: 04/20/2020)\n" +
                    "• yyyy-MM-dd (ej: 2020-04-20)\n\n" +
                    "Fecha ingresada: " + fechaC,
                    "⚠️ Formato de Fecha Inválido", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Parsear fecha de entrega
        if (!fechaI.isEmpty()) {
            for (SimpleDateFormat format : dateFormats) {
                try {
                    fechaEntregaDate = format.parse(fechaI);
                    break;
                } catch (ParseException e) {
                    // Continuar con el siguiente formato
                }
            }
            
            if (fechaEntregaDate == null) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error: Formato de fecha de entrega inválido.\n\n" +
                    "📅 Formatos aceptados:\n" +
                    "• dd/MM/yyyy (ej: 20/04/2020)\n" +
                    "• MM/dd/yyyy (ej: 04/20/2020)\n" +
                    "• yyyy-MM-dd (ej: 2020-04-20)\n\n" +
                    "Fecha ingresada: " + fechaI,
                    "⚠️ Formato de Fecha Inválido", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Validación: La fecha de entrega no puede ser anterior a la fecha de carga
        if (fechaCargaDate != null && fechaEntregaDate != null && fechaEntregaDate.before(fechaCargaDate)) {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            JOptionPane.showMessageDialog(
                this, 
                "❌ Error: La fecha de entrega no puede ser anterior a la fecha de carga.\n\n" +
                "📅 Fecha de carga: " + displayFormat.format(fechaCargaDate) + "\n" +
                "📅 Fecha de entrega: " + displayFormat.format(fechaEntregaDate) + "\n\n" +
                "Por favor, corrija las fechas e intente nuevamente.",
                "⚠️ Fechas Inválidas", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        PeticionDto nuevaPeticion = new PeticionDto(peticionDto.getId(), obra, fechaCargaDate, fechaEntregaDate, sucursal, paciente, peticionDto.getPracticas());
        try {
            peticionController.modificarPeticion(nuevaPeticion);
            peticionesTodas.actualizarTablaPeticiones();
            
            JOptionPane.showMessageDialog(
                this,
                "✅ Petición actualizada correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private SucursalDto findSucursalByDisplayText(String displayText) {
        for (SucursalDto sucursal : sucursales) {
            String sucursalDisplayText = sucursal.getNumero() + " _ " + sucursal.getId();
            if (sucursalDisplayText.equals(displayText)) {
                return sucursal;
            }
        }
        return null;
    }

    private PacienteDto findPacienteByDisplayText(String displayText) {
        for (PacienteDto pacienteDto : pacientes) {
            String sucursalDisplayText = pacienteDto.getNombre() + " - " + pacienteDto.getApellido();
            if (sucursalDisplayText.equals(displayText)) {
                return pacienteDto;
            }
        }
        return null;
    }

    private void onCancel() {
        dispose();
    }
}
