package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.enums.Genero;
import main.uade.edu.ar.exceptions.PacienteYaExisteException;

import javax.swing.*;
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

    private ButtonGroup generoButtonGroup;

    private JRadioButton generoRadioButtonFemenino;
    private JRadioButton generoRadioButtonMasculino;

    private PacienteController pacienteController;

    private PacientesTodas pacientesTodas;


    public AgregarPaciente(PacienteController pacienteController, PacientesTodas pacientesTodas) {
        this.pacienteController = pacienteController;
        this.pacientesTodas = pacientesTodas;
        initializeUI();
        setListeners();
    }

    private void initializeUI() {
        setTitle("Crear Nuevo Paciente");
        
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel nombrePacienteLabel = new JLabel("Nombre del paciente:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(nombrePacienteLabel, gbc);

        nombreTextField = createPlaceholderTextField("Ingrese el nombre del paciente");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPane.add(nombreTextField, gbc);

        JLabel apellidoLabel = new JLabel("Apellido:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        contentPane.add(apellidoLabel, gbc);

        apellidoTextField = createPlaceholderTextField("Ingrese el apellido");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        contentPane.add(apellidoTextField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        contentPane.add(emailLabel, gbc);

        emailTextField = createPlaceholderTextField("Ingrese el mail");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        contentPane.add(emailTextField, gbc);

        JLabel dniLabel = new JLabel("Dni:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        contentPane.add(dniLabel, gbc);

        dniTextField = createPlaceholderTextField("Ingrese el dni");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        contentPane.add(dniTextField, gbc);

        JLabel edadLabel = new JLabel("Edad:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        contentPane.add(edadLabel, gbc);

        edadTextField = createPlaceholderTextField("Ingrese la edad");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        contentPane.add(edadTextField, gbc);

        JLabel generoLabel = new JLabel("Genero:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        contentPane.add(generoLabel, gbc);

        JPanel generoPanel = new JPanel();
        generoPanel.setLayout(new BoxLayout(generoPanel, BoxLayout.X_AXIS));
        generoPanel.setBackground(contentPane.getBackground());
        generoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        generoRadioButtonMasculino = new JRadioButton("Masculino");
        generoRadioButtonFemenino = new JRadioButton("Femenino");
        
        generoButtonGroup = new ButtonGroup();
        generoButtonGroup.add(generoRadioButtonMasculino);
        generoButtonGroup.add(generoRadioButtonFemenino);
        
        generoPanel.add(generoRadioButtonMasculino);
        generoPanel.add(Box.createHorizontalStrut(10)); // Espacio fijo de 10px
        generoPanel.add(generoRadioButtonFemenino);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0.0; // No expandir horizontalmente
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(generoPanel, gbc);

        JLabel domicilioLabel = new JLabel("Domicilio:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        contentPane.add(domicilioLabel, gbc);

        domicilioTextField = createPlaceholderTextField("Ingrese el domicilio");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        contentPane.add(domicilioTextField, gbc);

        guardarButton = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        contentPane.add(guardarButton, gbc);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(guardarButton);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(450, 500); // Tamaño más grande para mostrar todos los campos y el botón
        setLocationRelativeTo(null); // Centrar el diálogo en la pantalla
    }

    private JTextField createPlaceholderTextField(String placeholderText) {
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createCompoundBorder(textField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setForeground(Color.GRAY);

        textField.setText(placeholderText);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }

    private void setListeners() {
        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        });
    }

    private void onGuardar() {
        String nombrePaciente = nombreTextField.getText();
        String apellidoPaciente = apellidoTextField.getText();
        String emailPaciente = emailTextField.getText();
        String dniPaciente = dniTextField.getText();
        String edadPaciente = edadTextField.getText();
        String domicilioPaciente = domicilioTextField.getText();
        
        if (nombrePaciente.isEmpty() || nombrePaciente.equals("Ingrese el nombre del paciente")) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el nombre del paciente", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (apellidoPaciente.isEmpty() || apellidoPaciente.equals("Ingrese el apellido")) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el apellido", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (emailPaciente.isEmpty() || emailPaciente.equals("Ingrese el mail")) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el email", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (dniPaciente.isEmpty() || dniPaciente.equals("Ingrese el dni")) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese el DNI", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (edadPaciente.isEmpty() || edadPaciente.equals("Ingrese la edad")) {
            JOptionPane.showMessageDialog(this, "❌ Por favor, ingrese la edad", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (domicilioPaciente.isEmpty() || domicilioPaciente.equals("Ingrese el domicilio")) {
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
            PacienteDto nuevoPaciente = new PacienteDto(randomId, Integer.parseInt(edadPaciente), generoPaciente, nombrePaciente, Integer.parseInt(dniPaciente), domicilioPaciente, emailPaciente, apellidoPaciente);
            
            pacienteController.crearPaciente(nuevoPaciente);
            pacientesTodas.actualizarTablaPacientes();
            
            JOptionPane.showMessageDialog(this, "✅ Paciente creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (PacienteYaExisteException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(), "Paciente Duplicado", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Error: La edad y el DNI deben ser números válidos", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error al crear el paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }


}

