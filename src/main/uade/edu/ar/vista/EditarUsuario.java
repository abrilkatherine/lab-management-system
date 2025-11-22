package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.enums.Roles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;


public class EditarUsuario extends JDialog {

    private JPanel contentPane;

    private JTextField nombreTextField;

    private JPasswordField contraseniaTextField;

    private JTextField fechanacimientoTextField;

    private JComboBox<Roles> rolComboBox;
    private JButton guardarButton;
    private JButton cancelarCambiosButton;
    private UsuarioDto usuario;
    private SucursalYUsuarioController sucursalYUsuarioController;
    private UsuariosTodos usuariosTodos;

    public EditarUsuario(UsuarioDto usuario, SucursalYUsuarioController sucursalYUsuarioController, UsuariosTodos usuariosTodos) {
        this.usuario = usuario;
        this.sucursalYUsuarioController = sucursalYUsuarioController;
        this.usuariosTodos = usuariosTodos;
        initializeUI();
        setListeners();
        cargarDatos();
    }

    private void initializeUI() {
        // Configurar el título del diálogo
        setTitle("Editar Usuario");
        
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nombre del usuario
        JLabel nombreUsuarioLabel = new JLabel("Nombre del usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(nombreUsuarioLabel, gbc);

        nombreTextField = createPlaceholderTextField("Ingrese el nombre del usuario");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPane.add(nombreTextField, gbc);

        // Contraseña del usuario
        JLabel contraseniaLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        contentPane.add(contraseniaLabel, gbc);

        JPanel contraseniaPanel = new JPanel(new BorderLayout());
        contraseniaPanel.setBackground(contentPane.getBackground());
        
        contraseniaTextField = new JPasswordField();
        contraseniaTextField.setBorder(BorderFactory.createCompoundBorder(contraseniaTextField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        contraseniaTextField.setEchoChar('•'); // Siempre ocultar caracteres
        contraseniaTextField.setForeground(Color.BLACK);
        contraseniaPanel.add(contraseniaTextField, BorderLayout.CENTER);
        
        // Label que indica que hay una contraseña guardada
        JLabel contraseniaInfoLabel = new JLabel("(Dejar vacío para mantener la actual)");
        contraseniaInfoLabel.setForeground(Color.GRAY);
        contraseniaInfoLabel.setFont(contraseniaInfoLabel.getFont().deriveFont(Font.ITALIC, 10f));
        contraseniaPanel.add(contraseniaInfoLabel, BorderLayout.SOUTH);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        contentPane.add(contraseniaPanel, gbc);

        // email
        JLabel fechanacimientoLabel = new JLabel("Fecha de nacimiento:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        contentPane.add(fechanacimientoLabel, gbc);

        fechanacimientoTextField = createPlaceholderTextField("MM/DD/AAAA");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        contentPane.add(fechanacimientoTextField, gbc);

        JLabel rolLabel = new JLabel("Rol:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        contentPane.add(rolLabel, gbc);

        rolComboBox = new JComboBox<>(Roles.values());
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        contentPane.add(rolComboBox, gbc);

        // Botón Guardar
        guardarButton = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        contentPane.add(guardarButton, gbc);

        cancelarCambiosButton = new JButton("Cancelar Cambios");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        contentPane.add(cancelarCambiosButton, gbc);

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

        setSize(450, 400); // Tamaño más grande para mostrar todos los campos y el botón
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

        cancelarCambiosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void cargarDatos() {
        nombreTextField.setText(usuario.getNombre());
        // No mostrar la contraseña, dejar el campo vacío
        // El usuario deberá ingresar una nueva contraseña si desea cambiarla
        contraseniaTextField.setText("");
        fechanacimientoTextField.setText(String.valueOf(usuario.getNacimiento().toString()));
        rolComboBox.setSelectedItem(usuario.getRol());
    }


    private void onGuardar() {
        // Acciones de guardar
        String nombreUsuario = nombreTextField.getText();
        char[] contraseniaChars = contraseniaTextField.getPassword();
        String contraseniaUsuario = new String(contraseniaChars);
        String fechanacimientoUsuario = fechanacimientoTextField.getText();
        Roles rolUsuario = (Roles) rolComboBox.getSelectedItem();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date fechaNacimiento = null;

        if (!fechanacimientoUsuario.isEmpty() && !fechanacimientoUsuario.equals("MMM dd, yyyy, hh:mm:ss a")) {
            try {
                fechaNacimiento = dateFormat.parse(fechanacimientoUsuario);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Si el campo de contraseña está vacío, mantener la contraseña original
        boolean contraseñaCambiada = false;
        String contraseniaFinal = contraseniaUsuario;
        if (contraseniaUsuario == null || contraseniaUsuario.trim().isEmpty()) {
            // Mantener la contraseña original (puede estar encriptada o en texto plano)
            contraseniaFinal = usuario.getContrasenia();
        } else {
            // Se ingresó una nueva contraseña
            contraseñaCambiada = true;
        }

        UsuarioDto nuevoUsuario = new UsuarioDto(usuario.getId(), nombreUsuario, contraseniaFinal, fechaNacimiento, rolUsuario);
        try {
            sucursalYUsuarioController.modificarUsuario(nuevoUsuario);
            usuariosTodos.actualizarTablaUsuarios();
            
            // Mostrar mensaje de confirmación
            String mensaje = "✅ Usuario actualizado exitosamente";
            if (contraseñaCambiada) {
                mensaje += "\n🔒 La contraseña ha sido actualizada";
            }
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // Imprimir información de la excepción
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onCancel() {
        dispose();
    }
}