package main.uade.edu.ar.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.SucursalDto;
import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.StyleUtils;

public class EditarSucursal extends JDialog {
    private JPanel contentPane;
    private JTextField numeroSucursalTextField;
    private JTextField direccionTextField;
    private JComboBox<String> responsableComboBox;
    private JButton guardarButton;
    private JButton cancelarButton;
    
    private SucursalDto sucursal;
    private List<UsuarioDto> usuarios;
    private SucursalYUsuarioController sucursalYUsuarioController;
    private SucursalTodas sucursalTodas;

    public EditarSucursal(SucursalDto sucursal, SucursalYUsuarioController sucursalYUsuarioController, SucursalTodas sucursalTodas) {
        this.sucursalTodas = sucursalTodas;
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.sucursal = sucursal;

        // Validar permisos antes de abrir la ventana
        PermissionManager permissionManager = PermissionManager.getInstance();
        if (!permissionManager.puedeGestionarSucursales()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ No tiene permisos para editar sucursales.\n" +
                "Requiere rol: ADMINISTRADOR",
                "Acceso Denegado",
                JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }

        cargarUsuarios();
        initializeUI();
        setListeners();
        cargarDatos();
    }

    private void cargarUsuarios() {
        usuarios = sucursalYUsuarioController.getUsuariosParaResponsableTecnico();
    }

    private void initializeUI() {
        setTitle("Editar Sucursal");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("✏️ Editar Sucursal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(StyleUtils.DARK_TEXT);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(StyleUtils.WHITE);

        JPanel datosSucursalPanel = createSection("Datos de la Sucursal");
        datosSucursalPanel.add(createFormField("Número de Sucursal:", numeroSucursalTextField = createStyledTextField()));
        datosSucursalPanel.add(Box.createVerticalStrut(12));
        datosSucursalPanel.add(createFormField("Dirección:", direccionTextField = createStyledTextField()));
        datosSucursalPanel.add(Box.createVerticalStrut(12));
        
        // ComboBox de responsable estilizado
        JPanel responsablePanel = new JPanel(new BorderLayout(10, 5));
        responsablePanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        responsablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel responsableLabel = new JLabel("Responsable Técnico:");
        responsableLabel.setFont(StyleUtils.TEXT_FONT);
        responsableLabel.setForeground(StyleUtils.DARK_TEXT);
        responsablePanel.add(responsableLabel, BorderLayout.NORTH);
        
        responsableComboBox = new JComboBox<>();
        for (UsuarioDto usuario : usuarios) {
            String displayText = usuario.getNombre() + " - " + usuario.getRol();
            responsableComboBox.addItem(displayText);
            if (usuario.equals(sucursal.getResponsableTecnico())) {
                responsableComboBox.setSelectedItem(displayText);
            }
        }
        responsableComboBox.setFont(StyleUtils.TEXT_FONT);
        responsableComboBox.setPreferredSize(new Dimension(0, 42));
        responsableComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        responsableComboBox.setBackground(StyleUtils.WHITE);
        responsableComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        responsablePanel.add(responsableComboBox, BorderLayout.CENTER);
        
        datosSucursalPanel.add(responsablePanel);
        formPanel.add(datosSucursalPanel);
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

        setSize(750, 550);
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
        numeroSucursalTextField.setText(String.valueOf(sucursal.getNumero()));
        direccionTextField.setText(sucursal.getDireccion());
        responsableComboBox.setSelectedItem(getDisplayText(sucursal.getResponsableTecnico()));
    }

    private String getDisplayText(UsuarioDto usuario) {
        return usuario.getNombre() + " - " + usuario.getRol();
    }
    
    private UsuarioDto findUsuarioByDisplayText(String displayText) {
        for (UsuarioDto usuario : usuarios) {
            String usuarioDisplayText = usuario.getNombre() + " - " + usuario.getRol();
            if (usuarioDisplayText.equals(displayText)) {
                return usuario;
            }
        }
        return null;
    }

    private void onGuardar() {
        String numeroSucursal = numeroSucursalTextField.getText();
        String direccion = direccionTextField.getText();
        String responsableText = (String) responsableComboBox.getSelectedItem();
        UsuarioDto responsable = findUsuarioByDisplayText(responsableText);

        SucursalDto sucursalEditada = new SucursalDto(sucursal.getId(), Integer.parseInt(numeroSucursal), direccion, responsable);
        try {
            sucursalYUsuarioController.modificarSucursal(sucursalEditada);
            sucursalTodas.actualizarTablaSucursales();
            
            JOptionPane.showMessageDialog(
                this,
                "✅ Sucursal actualizada correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}
