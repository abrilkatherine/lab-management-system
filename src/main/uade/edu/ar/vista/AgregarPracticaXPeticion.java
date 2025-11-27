package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.dto.PracticaDto;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AgregarPracticaXPeticion extends JDialog {
    private JPanel contentPane;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField horasFaltantesTextField;
    private JTextField grupoPracticaTextField;
    private JTextField nombrePracticaTextField;
    private JTextField codigoPracticaTextField;
    
    private PeticionController peticionController;
    private PracticasXPeticion practicasXPeticion;
    private int idPeticion;
    
    public AgregarPracticaXPeticion(PeticionController peticionController, int idPeticion, PracticasXPeticion practicasXPeticion){
        this.peticionController = peticionController;
        this.idPeticion = idPeticion;
        this.practicasXPeticion = practicasXPeticion;
        
        // Validar permisos antes de abrir la ventana
        PermissionManager permissionManager = PermissionManager.getInstance();
        if (!permissionManager.puedeAgregarPracticas()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ No tiene permisos para agregar prácticas.\n" +
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
        setTitle("Nueva Práctica");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("➕ Nueva Práctica");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(StyleUtils.DARK_TEXT);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(StyleUtils.WHITE);

        JPanel datosPracticaPanel = createSection("Datos de la Práctica");
        datosPracticaPanel.add(createFormField("Código:", codigoPracticaTextField = createStyledTextField()));
        datosPracticaPanel.add(Box.createVerticalStrut(12));
        datosPracticaPanel.add(createFormField("Nombre:", nombrePracticaTextField = createStyledTextField()));
        datosPracticaPanel.add(Box.createVerticalStrut(12));
        
        JPanel grupoHorasPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        grupoHorasPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        grupoHorasPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        grupoHorasPanel.add(createFormField("Grupo:", grupoPracticaTextField = createStyledTextField()));
        grupoHorasPanel.add(createFormField("Horas Faltantes:", horasFaltantesTextField = createStyledTextField()));
        datosPracticaPanel.add(grupoHorasPanel);
        
        formPanel.add(datosPracticaPanel);
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
        setAlwaysOnTop(true);
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
        String codigoPractica = codigoPracticaTextField.getText();
        String nombrePractica = nombrePracticaTextField.getText();
        String grupoPractica = grupoPracticaTextField.getText();
        String horasFaltantes = horasFaltantesTextField.getText();

        // Validaciones
        if (codigoPractica == null || codigoPractica.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el código de la práctica", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (nombrePractica == null || nombrePractica.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el nombre de la práctica", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (grupoPractica == null || grupoPractica.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el grupo", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (horasFaltantes == null || horasFaltantes.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese las horas faltantes", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Random random = new Random();
            int randomId = random.nextInt(1, 900);
            PracticaDto nuevaPractica = new PracticaDto(randomId, Integer.parseInt(codigoPractica), nombrePractica, Integer.parseInt(grupoPractica), Float.parseFloat(horasFaltantes));

            peticionController.crearPractica(idPeticion, nuevaPractica);
            practicasXPeticion.actualizarDatos();
            
            JOptionPane.showMessageDialog(
                this,
                "✅ Práctica creada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese valores numéricos válidos", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al crear la práctica: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}
