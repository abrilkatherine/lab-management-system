package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.dto.PracticaDto;
import main.uade.edu.ar.dto.ResultadoDto;
import main.uade.edu.ar.enums.TipoResultado;
import main.uade.edu.ar.util.StyleUtils;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.ResultadoUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class EditarPractica extends JDialog {
    private JPanel contentPane;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField horasFaltantesTextField;
    private JTextField grupoPracticaTextField;
    private JTextField nombrePracticaTextField;
    private JTextField codigoPracticaTextField;
    private JTextField valorResultadoTextField;
    private JLabel tipoResultadoLabel;
    private PeticionController peticionController;
    private PracticasXPeticion practicasXPeticion;
    private PracticaDto practica;
    public EditarPractica(PeticionController peticionController, PracticaDto practica, PracticasXPeticion practicasXPeticion){
        this.peticionController = peticionController;
        this.practica = practica;
        this.practicasXPeticion = practicasXPeticion;
        
        // Validar permisos para cargar resultados
        PermissionManager permissionManager = PermissionManager.getInstance();
        if (!permissionManager.puedeCargarResultados()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ No tiene permisos para cargar resultados.\n" +
                "Requiere rol: ADMINISTRADOR o LABORATORISTA",
                "Acceso Denegado",
                JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }
        
        initializeUI();
        setListeners();
        cargarDatos();
    }

    private void initializeUI() {
        setTitle("Editar Práctica");
        
        contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(StyleUtils.WHITE);
        contentPane.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(StyleUtils.WHITE);
        JLabel titleLabel = new JLabel("✏️ Editar Práctica");
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
        formPanel.add(Box.createVerticalStrut(20));

        JPanel resultadoPanel = createSection("Resultado");
        resultadoPanel.add(createFormField("Valor del Resultado:", valorResultadoTextField = createStyledTextField()));
        resultadoPanel.add(Box.createVerticalStrut(12));
        
        // Listener para actualizar el tipo automáticamente
        valorResultadoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actualizarTipoResultado();
            }
        });
        
        // Label para mostrar el tipo determinado automáticamente
        JPanel tipoPanel = new JPanel(new BorderLayout(10, 5));
        tipoPanel.setBackground(StyleUtils.VERY_LIGHT_GRAY);
        tipoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel tipoTituloLabel = new JLabel("Resultado del estudio:");
        tipoTituloLabel.setFont(StyleUtils.TEXT_FONT);
        tipoTituloLabel.setForeground(StyleUtils.DARK_TEXT);
        tipoPanel.add(tipoTituloLabel, BorderLayout.NORTH);
        
        tipoResultadoLabel = new JLabel("");
        tipoResultadoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tipoResultadoLabel.setForeground(StyleUtils.MEDIUM_GRAY);
        tipoResultadoLabel.setOpaque(true);
        tipoResultadoLabel.setBackground(StyleUtils.WHITE);
        tipoResultadoLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.MEDIUM_GRAY, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        tipoPanel.add(tipoResultadoLabel, BorderLayout.CENTER);
        
        resultadoPanel.add(tipoPanel);
        formPanel.add(resultadoPanel);

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
    private void cargarDatos() {
        codigoPracticaTextField.setText(String.valueOf(practica.getCodigo()));
        nombrePracticaTextField.setText(practica.getNombre());
        grupoPracticaTextField.setText(String.valueOf(practica.getGrupo()));
        horasFaltantesTextField.setText(String.valueOf(practica.getHorasFaltantes()));

        ResultadoDto resultado = practica.getResultado();
        if (resultado != null) {
            if(resultado.getTipoResultado() == TipoResultado.RESERVADO) {
                valorResultadoTextField.setText("Retirar por sucursal");
                // Mostrar directamente el tipo RESERVADO sin recalcular
                mostrarTipoResultado(TipoResultado.RESERVADO);
            } else {
                valorResultadoTextField.setText(String.valueOf(resultado.getValor()));
                // Recalcular el tipo para resultados normales/críticos
                actualizarTipoResultado();
            }
        } else {
            // No hay resultado, dejar el campo vacío y limpiar el tipo
            valorResultadoTextField.setText("");
            limpiarTipoResultado();
        }
    }

    private void onGuardar() {
        // Acciones de guardar
        String codigoPractica = codigoPracticaTextField.getText();
        String nombrePractica = nombrePracticaTextField.getText();
        String grupoPractica = grupoPracticaTextField.getText();
        String horasFaltantes = horasFaltantesTextField.getText();
        String valorResultado = valorResultadoTextField.getText();
        
        // Determinar automáticamente el tipo de resultado basado en el valor
        TipoResultado tipoResultado = ResultadoUtil.determinarTipoResultado(valorResultado);

        ResultadoDto nuevoresultado = new ResultadoDto(valorResultado, tipoResultado);
        PracticaDto nuevaPractica = new PracticaDto(practica.getId(), Integer.parseInt(codigoPractica), nombrePractica, Integer.parseInt(grupoPractica), Float.parseFloat(horasFaltantes), nuevoresultado);

        try {
            peticionController.modificarPractica(nuevaPractica);
            practicasXPeticion.actualizarDatos();
            dispose();
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // Imprimir información de la excepción
            // Opcional: Mostrar un mensaje de error al usuario
            JOptionPane.showMessageDialog(this, "Error al crear el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
    
    /**
     * Actualiza el label del tipo de resultado basado en el valor ingresado.
     * Cambia el color según el tipo:
     * - NORMAL: Verde
     * - CRITICO: Naranja/Amarillo
     * - RESERVADO: Rojo
     * Si el campo está vacío, limpia el label.
     */
    private void actualizarTipoResultado() {
        String valor = valorResultadoTextField.getText();
        
        // Si el campo está vacío, limpiar el label
        if (valor == null || valor.trim().isEmpty()) {
            limpiarTipoResultado();
            return;
        }
        
        TipoResultado tipo = ResultadoUtil.determinarTipoResultado(valor);
        mostrarTipoResultado(tipo);
    }
    
    /**
     * Muestra el tipo de resultado en el label con el color correspondiente.
     * 
     * @param tipo el tipo de resultado a mostrar
     */
    private void mostrarTipoResultado(TipoResultado tipo) {
        tipoResultadoLabel.setText(tipo.toString());
        
        switch (tipo) {
            case NORMAL:
                tipoResultadoLabel.setForeground(StyleUtils.SUCCESS_GREEN);
                break;
            case CRITICO:
                tipoResultadoLabel.setForeground(new Color(255, 152, 0)); // Naranja
                break;
            case RESERVADO:
                tipoResultadoLabel.setForeground(StyleUtils.DANGER_RED);
                break;
        }
    }
    
    /**
     * Limpia el label del tipo de resultado cuando no hay valor ingresado.
     */
    private void limpiarTipoResultado() {
        tipoResultadoLabel.setText("");
        tipoResultadoLabel.setForeground(StyleUtils.MEDIUM_GRAY);
    }
}
