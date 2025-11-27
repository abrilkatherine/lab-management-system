package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.enums.Genero;
import main.uade.edu.ar.exceptions.PacienteYaExisteException;
import main.uade.edu.ar.util.ValidacionUtil;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AgregarPaciente extends JDialog {
    private JPanel contentPane;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField dniTextField;
    private JTextField emailTextField;
    private JTextField domicilioTextField;
    private JTextField edadTextField;
    private JButton guardarButton;
    private JButton cancelarButton;
    private ButtonGroup generoButtonGroup;
    private JRadioButton generoRadioButtonFemenino;
    private JRadioButton generoRadioButtonMasculino;
    
    private PacienteController pacienteController;
    private PacientesTodas pacientesTodas;

    public AgregarPaciente(PacienteController pacienteController, PacientesTodas pacientesTodas) {
        this.pacienteController = pacienteController;
        this.pacientesTodas = pacientesTodas;
        
        // Validar permisos antes de abrir la ventana
        PermissionManager permissionManager = PermissionManager.getInstance();
        if (!permissionManager.puedeAgregarPacientes()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ No tiene permisos para agregar pacientes.\n" +
                "Requiere rol: ADMINISTRADOR o RECEPCIONISTA",
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
        setTitle("Crear Nuevo Paciente");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("➕ Nuevo Paciente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(StyleUtils.DARK_TEXT);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(StyleUtils.WHITE);

        JPanel datosPersonalesPanel = createSection("Datos Personales");
        datosPersonalesPanel.add(createFormField("Nombre:", nombreTextField = createStyledTextField()));
        datosPersonalesPanel.add(Box.createVerticalStrut(12));
        datosPersonalesPanel.add(createFormField("Apellido:", apellidoTextField = createStyledTextField()));
        datosPersonalesPanel.add(Box.createVerticalStrut(12));
        datosPersonalesPanel.add(createFormField("DNI:", dniTextField = createStyledTextField()));
        datosPersonalesPanel.add(Box.createVerticalStrut(12));
        datosPersonalesPanel.add(createFormField("Email:", emailTextField = createStyledTextField()));
        datosPersonalesPanel.add(Box.createVerticalStrut(12));
        
        JPanel edadGeneroPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        edadGeneroPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        edadGeneroPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        edadGeneroPanel.add(createFormField("Edad:", edadTextField = createStyledTextField()));
        
        JPanel generoFieldPanel = new JPanel(new BorderLayout(10, 5));
        generoFieldPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        JLabel generoLabel = new JLabel("Género:");
        generoLabel.setFont(StyleUtils.TEXT_FONT);
        generoLabel.setForeground(StyleUtils.DARK_TEXT);
        generoFieldPanel.add(generoLabel, BorderLayout.NORTH);
        
        generoButtonGroup = new ButtonGroup();
        JPanel generoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        generoPanel.setBackground(StyleUtils.WHITE);
        generoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        generoRadioButtonFemenino = new JRadioButton("👩 Femenino");
        generoRadioButtonFemenino.setFont(StyleUtils.TEXT_FONT);
        generoRadioButtonFemenino.setBackground(StyleUtils.WHITE);
        generoRadioButtonFemenino.setFocusPainted(false);
        
        generoRadioButtonMasculino = new JRadioButton("👨 Masculino");
        generoRadioButtonMasculino.setFont(StyleUtils.TEXT_FONT);
        generoRadioButtonMasculino.setBackground(StyleUtils.WHITE);
        generoRadioButtonMasculino.setFocusPainted(false);
        
        generoButtonGroup.add(generoRadioButtonFemenino);
        generoButtonGroup.add(generoRadioButtonMasculino);
        generoPanel.add(generoRadioButtonFemenino);
        generoPanel.add(generoRadioButtonMasculino);
        
        generoFieldPanel.add(generoPanel, BorderLayout.CENTER);
        edadGeneroPanel.add(generoFieldPanel);
        
        datosPersonalesPanel.add(edadGeneroPanel);
        datosPersonalesPanel.add(Box.createVerticalStrut(12));
        datosPersonalesPanel.add(createFormField("Domicilio:", domicilioTextField = createStyledTextField()));
        
        formPanel.add(datosPersonalesPanel);
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

        setSize(750, 750);
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
        String nombrePaciente = nombreTextField.getText();
        String apellidoPaciente = apellidoTextField.getText();
        String emailPaciente = emailTextField.getText();
        String dniPaciente = dniTextField.getText();
        String edadPaciente = edadTextField.getText();
        String domicilioPaciente = domicilioTextField.getText();
        
        if (nombrePaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el nombre del paciente", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (apellidoPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el apellido", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (emailPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el email", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (dniPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el DNI", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!ValidacionUtil.esDniValido(dniPaciente)) {
            JOptionPane.showMessageDialog(this, ValidacionUtil.getMensajeErrorDni(), "DNI Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (edadPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese la edad", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!ValidacionUtil.esEdadValida(edadPaciente)) {
            JOptionPane.showMessageDialog(this, ValidacionUtil.getMensajeErrorEdad(), "Edad Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (domicilioPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el domicilio", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!generoRadioButtonMasculino.isSelected() && !generoRadioButtonFemenino.isSelected()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, seleccione un género", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Genero generoPaciente;
        if(generoRadioButtonMasculino.isSelected()){
            generoPaciente = Genero.MASCULINO;
        } else {
            generoPaciente = Genero.FEMENINO;
        }
        
        try {
            Random random = new Random();
            int randomId = random.nextInt(1, 900);
            int dniValidado = ValidacionUtil.parsearDni(dniPaciente);
            int edadValidada = ValidacionUtil.parsearEdad(edadPaciente);
            
            PacienteDto nuevoPaciente = new PacienteDto(randomId, edadValidada, generoPaciente, nombrePaciente, dniValidado, domicilioPaciente, emailPaciente, apellidoPaciente);
            
            pacienteController.crearPaciente(nuevoPaciente);
            pacientesTodas.actualizarTablaPacientes();
            
            JOptionPane.showMessageDialog(this, "✅ Paciente creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (PacienteYaExisteException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(), "Paciente Duplicado", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al crear el paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}
