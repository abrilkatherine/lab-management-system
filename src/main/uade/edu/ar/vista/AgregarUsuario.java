package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.enums.Roles;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;

public class AgregarUsuario extends JDialog {
    private JPanel contentPane;
    private JTextField nombreTextField;
    private JPasswordField contraseniaTextField;
    private JTextField fechanacimientoTextField;
    private JComboBox<Roles> rolComboBox;
    private JButton guardarButton;
    private JButton cancelarButton;
    
    private SucursalYUsuarioController sucursalYUsuarioController;
    private UsuariosTodos usuariosTodos;

    public AgregarUsuario(SucursalYUsuarioController sucursalYUsuarioController, UsuariosTodos usuariosTodos) {
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.usuariosTodos = usuariosTodos;
        
        // Validar permisos antes de abrir la ventana
        PermissionManager permissionManager = PermissionManager.getInstance();
        if (!permissionManager.puedeGestionarUsuarios()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ No tiene permisos para agregar usuarios.\n" +
                "Requiere rol: ADMINISTRADOR",
                "Acceso Denegado",
                JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }
        
        initializeUI();
        setListeners();
    }

    private void initializeUI() {
        setTitle("Crear Nuevo Usuario");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("➕ Nuevo Usuario");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(StyleUtils.DARK_TEXT);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(StyleUtils.WHITE);

        JPanel datosUsuarioPanel = createSection("Datos del Usuario");
        datosUsuarioPanel.add(createFormField("Nombre:", nombreTextField = createStyledTextField()));
        datosUsuarioPanel.add(Box.createVerticalStrut(12));
        
        // Campo de contraseña estilizado
        JPanel contraseniaFieldPanel = new JPanel(new BorderLayout(10, 5));
        contraseniaFieldPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        contraseniaFieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel contraseniaLabel = new JLabel("Contraseña:");
        contraseniaLabel.setFont(StyleUtils.TEXT_FONT);
        contraseniaLabel.setForeground(StyleUtils.DARK_TEXT);
        contraseniaFieldPanel.add(contraseniaLabel, BorderLayout.NORTH);
        
        contraseniaTextField = new JPasswordField();
        contraseniaTextField.setFont(StyleUtils.TEXT_FONT);
        contraseniaTextField.setForeground(StyleUtils.DARK_TEXT);
        contraseniaTextField.setBackground(StyleUtils.WHITE);
        contraseniaTextField.setPreferredSize(new Dimension(0, 42));
        contraseniaTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        contraseniaTextField.setEchoChar('•');
        
        // Efecto focus para contraseña
        contraseniaTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                contraseniaTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleUtils.PRIMARY_BLUE, 2),
                    new EmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                contraseniaTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        contraseniaFieldPanel.add(contraseniaTextField, BorderLayout.CENTER);
        datosUsuarioPanel.add(contraseniaFieldPanel);
        datosUsuarioPanel.add(Box.createVerticalStrut(12));
        
        datosUsuarioPanel.add(createFormField("Fecha de Nacimiento (dd/MM/yyyy):", 
            fechanacimientoTextField = createStyledTextField()));
        datosUsuarioPanel.add(Box.createVerticalStrut(12));
        
        // ComboBox de rol estilizado
        JPanel rolPanel = new JPanel(new BorderLayout(10, 5));
        rolPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        rolPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel rolLabel = new JLabel("Rol:");
        rolLabel.setFont(StyleUtils.TEXT_FONT);
        rolLabel.setForeground(StyleUtils.DARK_TEXT);
        rolPanel.add(rolLabel, BorderLayout.NORTH);
        
        rolComboBox = new JComboBox<>(Roles.values());
        rolComboBox.setFont(StyleUtils.TEXT_FONT);
        rolComboBox.setPreferredSize(new Dimension(0, 42));
        rolComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        rolComboBox.setBackground(StyleUtils.WHITE);
        rolComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        rolPanel.add(rolComboBox, BorderLayout.CENTER);
        
        datosUsuarioPanel.add(rolPanel);
        formPanel.add(datosUsuarioPanel);
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

        setSize(750, 600);
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

    private void onGuardar() {
        String nombreUsuario = nombreTextField.getText();
        char[] contraseniaChars = contraseniaTextField.getPassword();
        String contraseniaUsuario = new String(contraseniaChars);
        String fechanacimientoUsuario = fechanacimientoTextField.getText();
        Roles rolUsuario = (Roles) rolComboBox.getSelectedItem();

        // Validaciones
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el nombre del usuario", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (contraseniaUsuario == null || contraseniaUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese una contraseña", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Intentar múltiples formatos de fecha
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH),
            new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH),
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        };
        
        Date fechaNacimiento = null;
        for (SimpleDateFormat format : dateFormats) {
            try {
                fechaNacimiento = format.parse(fechanacimientoUsuario);
                break;
            } catch (ParseException e) {
                // Continuar con el siguiente formato
            }
        }
        
        if (fechaNacimiento == null) {
            JOptionPane.showMessageDialog(
                this, 
                "❌ Formato de fecha inválido.\n\n" +
                "📅 Formatos aceptados:\n" +
                "• dd/MM/yyyy (ej: 25/12/1990)\n" +
                "• MM/dd/yyyy (ej: 12/25/1990)\n" +
                "• yyyy-MM-dd (ej: 1990-12-25)", 
                "Error de Fecha", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Random random = new Random();
        int randomId = random.nextInt(1, 900);
        UsuarioDto nuevoUsuario = new UsuarioDto(randomId, nombreUsuario, contraseniaUsuario, fechaNacimiento, rolUsuario);

        try {
            sucursalYUsuarioController.crearUsuario(nuevoUsuario);
            usuariosTodos.actualizarTablaUsuarios();
            
            JOptionPane.showMessageDialog(
                this,
                "✅ Usuario creado exitosamente",
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
