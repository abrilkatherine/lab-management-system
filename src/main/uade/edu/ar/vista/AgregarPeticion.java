package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.dto.PeticionDto;
import main.uade.edu.ar.dto.SucursalDto;
import static main.uade.edu.ar.util.DateUtil.getFecha;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgregarPeticion extends JDialog {
    private JPanel contentPane;

    private JTextField obraSocial;

    private JTextField fechaCarga;

    private JTextField fechaEntrega;

    private JComboBox<String> sucursalComboBox;

    private JComboBox<String> pacienteComboBox;


    private JButton guardarButton;

    private PeticionController peticionController;

    private PeticionesTodas peticionesTodas;

    private List<SucursalDto> sucursales;

    private List<PacienteDto> pacientes;

    private PacienteController pacienteController;
    private SucursalYUsuarioController sucursalYUsuarioController;

    public AgregarPeticion(PeticionController peticionController, PeticionesTodas peticionesTodas) {
        this.peticionController = peticionController;
        this.peticionesTodas = peticionesTodas;
        try{
            this.sucursalYUsuarioController = SucursalYUsuarioController.getInstance();
            this.pacienteController = PacienteController.getInstance();
        }  catch (Exception e) {
            e.printStackTrace();
        }

        cargarSucursales();
        cargarPacientes();
        initializeUI();
        setListeners();
    }

    private void cargarSucursales() {
        sucursales = sucursalYUsuarioController.getAllSucursales();
    }

    private void cargarPacientes(){pacientes = pacienteController.getAllPacientes();}


    private void initializeUI() {
        // Configurar el título del diálogo
        setTitle("Crear Nueva Petición");
        
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel obraSocialLabel = new JLabel("Obra social:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(obraSocialLabel, gbc);

        obraSocial = createPlaceholderTextField("Ingrese la obra social");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPane.add(obraSocial, gbc);

        JLabel sucursalLabel = new JLabel("Sucursal:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        contentPane.add(sucursalLabel, gbc);

        sucursalComboBox = new JComboBox<>();
        for (SucursalDto sucursal : sucursales) {
            String displayText = sucursal.getNumero() + " - " + sucursal.getId();
            sucursalComboBox.addItem(displayText);
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        contentPane.add(sucursalComboBox, gbc);

        JLabel pacienteLabel = new JLabel("Paciente:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        contentPane.add(pacienteLabel, gbc);

        pacienteComboBox = new JComboBox<>();
        for (PacienteDto paciente : pacientes) {
            String displayText = paciente.getNombre() + " - " + paciente.getApellido();
            pacienteComboBox.addItem(displayText);
        }
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        contentPane.add(pacienteComboBox, gbc);

        JLabel fechaInicio = new JLabel("Fecha carga:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(fechaInicio, gbc);

        fechaCarga = createPlaceholderTextField("dd/MM/yyyy (ej: 20/04/2025)");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        contentPane.add(fechaCarga, gbc);

        JLabel fechaFin = new JLabel("Fecha entrega:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(fechaFin, gbc);

        fechaEntrega = createPlaceholderTextField("dd/MM/yyyy (ej: 21/04/2025)");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        contentPane.add(fechaEntrega, gbc);

        // Botón Guardar
        guardarButton = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 5;
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
    }

    private void onGuardar() {
        // Acciones de guardar
        String obra = obraSocial.getText();
        String sucursalText = (String) sucursalComboBox.getSelectedItem();
        SucursalDto sucursal = findSucursalByDisplayText(sucursalText);
        String pacienteText = (String) pacienteComboBox.getSelectedItem();
        PacienteDto paciente = findPacienteByDisplayText(pacienteText);
        String fechaC = fechaCarga.getText();
        String fechaI = fechaEntrega.getText();


        // Intentar múltiples formatos de fecha para mayor flexibilidad
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH),  // Formato latinoamericano
            new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH),  // Formato americano
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)  // Formato ISO
        };
        
        Date fechaCarga = null;
        Date fechaEntrega = null;

        // Parsear fecha de carga
        for (SimpleDateFormat format : dateFormats) {
            try {
                fechaCarga = format.parse(fechaC);
                break;
            } catch (ParseException e) {
                // Continuar con el siguiente formato
            }
        }
        
        if (fechaCarga == null) {
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

        // Parsear fecha de entrega
        for (SimpleDateFormat format : dateFormats) {
            try {
                fechaEntrega = format.parse(fechaI);
                break;
            } catch (ParseException e) {
                // Continuar con el siguiente formato
            }
        }
        
        if (fechaEntrega == null) {
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

        // Validación: La fecha de entrega no puede ser anterior a la fecha de carga
        if (fechaEntrega.before(fechaCarga)) {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            JOptionPane.showMessageDialog(
                this, 
                "❌ Error: La fecha de entrega no puede ser anterior a la fecha de carga.\n\n" +
                "📅 Fecha de carga: " + displayFormat.format(fechaCarga) + "\n" +
                "📅 Fecha de entrega: " + displayFormat.format(fechaEntrega) + "\n\n" +
                "Por favor, corrija las fechas e intente nuevamente.",
                "⚠️ Fechas Inválidas", 
                JOptionPane.WARNING_MESSAGE
            );
            return; // No continuar con la creación de la petición
        }

        Random random = new Random();
        int randomId = random.nextInt(1, 900);
        PeticionDto nuevaPeticion = new PeticionDto(randomId, obra, fechaCarga, fechaEntrega, sucursal, paciente);

        try {
            peticionController.crearPeticion(nuevaPeticion);
            peticionesTodas.actualizarTablaPeticiones();
            dispose();
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // Imprimir información de la excepción
            // Opcional: Mostrar un mensaje de error al usuario
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private SucursalDto findSucursalByDisplayText(String displayText) {
        for (SucursalDto sucursal : sucursales) {
            String sucursalDisplayText = sucursal.getNumero() + " - " + sucursal.getId();
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

