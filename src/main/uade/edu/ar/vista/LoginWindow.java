package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.factory.ControllerFactory;
import main.uade.edu.ar.util.SessionManager;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Ventana de login para autenticar usuarios en el sistema.
 * Utiliza Swing para crear una interfaz moderna y consistente con el resto de la aplicación.
 */
public class LoginWindow extends JFrame {
    
    private JTextField usuarioField;
    private JPasswordField contraseniaField;
    private JTextField contraseniaVisibleField; // Campo visible cuando se muestra la contraseña
    private JPanel passwordFieldPanel; // Panel que contiene ambos campos de contraseña
    private JButton togglePasswordButton; // Botón para mostrar/ocultar contraseña
    private boolean passwordVisible = false;
    private JButton loginButton;
    private JButton cancelButton;
    private SucursalYUsuarioController usuarioController;
    
    public LoginWindow() {
        try {
            // Obtener el controlador de usuarios
            ControllerFactory factory = ControllerFactory.getInstance();
            usuarioController = factory.getSucursalYUsuarioController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "❌ Error al inicializar el sistema: " + e.getMessage(),
                "Error de Inicialización",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            System.exit(1);
        }
        
        initializeComponents();
        setupLayout();
        setupEvents();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void initializeComponents() {
        // Aplicar Look & Feel moderno
        StyleUtils.setModernLookAndFeel();
        
        // Configurar la ventana
        setTitle("🏥 Lab Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Crear campos de texto modernos
        usuarioField = StyleUtils.createModernTextField(20);
        contraseniaField = StyleUtils.createModernPasswordField(20);
        contraseniaVisibleField = StyleUtils.createModernTextField(20);
        contraseniaVisibleField.setVisible(false);
        
        // Crear botón para mostrar/ocultar contraseña
        togglePasswordButton = new JButton("👁️") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo sutil en hover
                if (getModel().isRollover()) {
                    g2.setColor(StyleUtils.LIGHT_GRAY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        togglePasswordButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        togglePasswordButton.setForeground(StyleUtils.MEDIUM_GRAY);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordButton.setPreferredSize(new Dimension(45, 42));
        togglePasswordButton.setToolTipText("Mostrar/Ocultar contraseña");
        togglePasswordButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        // Crear botones
        loginButton = StyleUtils.createActionButton("🔐 Iniciar Sesión", "add");
        loginButton.setPreferredSize(new Dimension(220, 45));
        
        cancelButton = StyleUtils.createModernButton("❌ Cancelar", StyleUtils.SECONDARY_GRAY, StyleUtils.WHITE);
        cancelButton.setPreferredSize(new Dimension(220, 45));
    }
    
    /**
     * Configura el layout de la ventana
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con fondo degradado
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradiente de fondo sutil
                GradientPaint gradient = new GradientPaint(
                    0, 0, StyleUtils.VERY_LIGHT_GRAY,
                    0, getHeight(), StyleUtils.WHITE
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.dispose();
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Panel de título con icono separado
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        
        // Icono grande centrado arriba
        JLabel iconLabel = new JLabel("🏥");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        titlePanel.add(iconLabel, BorderLayout.NORTH);
        
        // Título sin emoji
        JLabel titleLabel = new JLabel("Lab Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(StyleUtils.PRIMARY_BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Iniciar Sesión");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(StyleUtils.SECONDARY_GRAY);
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        mainPanel.add(titlePanel, gbc);
        
        // Label y campo de usuario
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Panel para campos de entrada
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(15, 0, 15, 0);
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        fgbc.weightx = 1.0;
        
        // Label y campo de usuario
        JLabel usuarioLabel = new JLabel("👤 Usuario");
        usuarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        usuarioLabel.setForeground(StyleUtils.DARK_TEXT);
        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.insets = new Insets(0, 0, 8, 0);
        fieldsPanel.add(usuarioLabel, fgbc);
        
        JPanel usuarioPanel = new JPanel(new BorderLayout(0, 0));
        usuarioPanel.setOpaque(false);
        usuarioPanel.add(usuarioField, BorderLayout.CENTER);
        
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(45, 42));
        usuarioPanel.add(spacer, BorderLayout.EAST);
        
        fgbc.gridy = 1;
        fgbc.insets = new Insets(0, 0, 20, 0);
        fieldsPanel.add(usuarioPanel, fgbc);
        
        // Label y campo de contraseña
        JLabel contraseniaLabel = new JLabel("🔑 Contraseña");
        contraseniaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        contraseniaLabel.setForeground(StyleUtils.DARK_TEXT);
        fgbc.gridy = 2;
        fgbc.insets = new Insets(0, 0, 8, 0);
        fieldsPanel.add(contraseniaLabel, fgbc);
        
        // Panel para campo de contraseña con botón de ojo
        JPanel passwordPanel = new JPanel(new BorderLayout(0, 0));
        passwordPanel.setOpaque(false);
        
        // Panel interno para los campos (se alternan)
        passwordFieldPanel = new JPanel(new CardLayout());
        passwordFieldPanel.setOpaque(false);
        passwordFieldPanel.add(contraseniaField, "hidden");
        passwordFieldPanel.add(contraseniaVisibleField, "visible");
        
        passwordPanel.add(passwordFieldPanel, BorderLayout.CENTER);
        passwordPanel.add(togglePasswordButton, BorderLayout.EAST);
        
        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 0, 10, 0);
        fieldsPanel.add(passwordPanel, fgbc);
        
        // Link "¿Olvidaste tu contraseña?"
        JButton olvidoPasswordLink = new JButton("¿Olvidaste tu contraseña?");
        olvidoPasswordLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        olvidoPasswordLink.setForeground(StyleUtils.PRIMARY_BLUE);
        olvidoPasswordLink.setBorderPainted(false);
        olvidoPasswordLink.setContentAreaFilled(false);
        olvidoPasswordLink.setFocusPainted(false);
        olvidoPasswordLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        olvidoPasswordLink.setHorizontalAlignment(SwingConstants.RIGHT);
        
        olvidoPasswordLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                olvidoPasswordLink.setForeground(StyleUtils.PRIMARY_BLUE.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                olvidoPasswordLink.setForeground(StyleUtils.PRIMARY_BLUE);
            }
        });
        
        olvidoPasswordLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecuperacionPassword();
            }
        });
        
        fgbc.gridy = 4;
        fgbc.insets = new Insets(0, 0, 20, 0);
        fgbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(olvidoPasswordLink, fgbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(fieldsPanel, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);
        
        // Agregar panel principal a la ventana
        add(mainPanel, BorderLayout.CENTER);
        
        // Configurar tamaño y posición
        pack();
        setLocationRelativeTo(null);
        
        // Crear ícono
        setIconImage(createAppIcon());
    }
    
    /**
     * Configura los eventos de los componentes
     */
    private void setupEvents() {
        // Botón de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        // Botón de cancelar
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Enter en campos de texto para iniciar sesión
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        };
        
        usuarioField.addKeyListener(enterKeyAdapter);
        contraseniaField.addKeyListener(enterKeyAdapter);
        contraseniaVisibleField.addKeyListener(enterKeyAdapter);
        
        // Listener para el botón de mostrar/ocultar contraseña
        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }
        });
        
        // Sincronizar campos de contraseña
        contraseniaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!passwordVisible) {
                    contraseniaVisibleField.setText(new String(contraseniaField.getPassword()));
                }
            }
        });
        
        contraseniaVisibleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (passwordVisible) {
                    contraseniaField.setText(contraseniaVisibleField.getText());
                }
            }
        });
        
        // Focus inicial en el campo de usuario
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                usuarioField.requestFocus();
            }
        });
    }
    
    /**
     * Alterna entre mostrar y ocultar la contraseña
     */
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        
        CardLayout cardLayout = (CardLayout) passwordFieldPanel.getLayout();
        
        if (passwordVisible) {
            // Mostrar contraseña en texto plano
            String password = new String(contraseniaField.getPassword());
            contraseniaVisibleField.setText(password);
            cardLayout.show(passwordFieldPanel, "visible");
            togglePasswordButton.setText("🙈"); // Ojo cerrado
            contraseniaVisibleField.requestFocus();
            int pos = contraseniaVisibleField.getText().length();
            contraseniaVisibleField.setCaretPosition(pos);
        } else {
            // Ocultar contraseña
            String password = contraseniaVisibleField.getText();
            contraseniaField.setText(password);
            cardLayout.show(passwordFieldPanel, "hidden");
            togglePasswordButton.setText("👁️"); // Ojo abierto
            contraseniaField.requestFocus();
            // Mover el cursor al final
            int pos = contraseniaField.getPassword().length;
            contraseniaField.setCaretPosition(pos);
        }
    }
    
    /**
     * Realiza el proceso de autenticación
     */
    private void realizarLogin() {
        String nombreUsuario = usuarioField.getText().trim();
        String contrasenia;
        if (passwordVisible) {
            contrasenia = contraseniaVisibleField.getText();
        } else {
            contrasenia = new String(contraseniaField.getPassword());
        }
        
        // Validar campos
        if (nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ Por favor, ingrese su nombre de usuario",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE
            );
            usuarioField.requestFocus();
            return;
        }
        
        if (contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "❌ Por favor, ingrese su contraseña",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE
            );
            contraseniaField.requestFocus();
            return;
        }
        
        // Deshabilitar botones durante la autenticación
        loginButton.setEnabled(false);
        cancelButton.setEnabled(false);
        
        try {
            // Intentar autenticar
            UsuarioDto usuario = usuarioController.autenticarUsuario(nombreUsuario, contrasenia);
            
            if (usuario != null) {
                // Autenticación exitosa
                SessionManager.getInstance().setUsuarioActual(usuario);
                
                // Mostrar mensaje de bienvenida
                JOptionPane.showMessageDialog(
                    this,
                    "✅ Bienvenido, " + usuario.getNombre() + "!\n" +
                    "Rol: " + usuario.getRol(),
                    "Login Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // Abrir el menú principal
                abrirMenuPrincipal();
                
                // Cerrar la ventana de login
                dispose();
            } else {
                // Autenticación fallida
                JOptionPane.showMessageDialog(
                    this,
                    "❌ Usuario o contraseña incorrectos.\n\n" +
                    "Por favor, verifique sus credenciales e intente nuevamente.",
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE
                );
                
                // Limpiar campo de contraseña
                contraseniaField.setText("");
                contraseniaVisibleField.setText("");
                if (passwordVisible) {
                    contraseniaVisibleField.requestFocus();
                } else {
                    contraseniaField.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "❌ Error al autenticar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } finally {
            // Rehabilitar botones
            loginButton.setEnabled(true);
            cancelButton.setEnabled(true);
        }
    }
    
    /**
     * Muestra un diálogo para recuperar la contraseña
     */
    private void mostrarRecuperacionPassword() {
        String nombreUsuario = JOptionPane.showInputDialog(
            this,
            "Ingrese su nombre de usuario:",
            "Recuperar Contraseña",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return; // Usuario canceló o no ingresó nada
        }
        
        try {
            UsuarioDto usuario = usuarioController.getUsuarioPorNombre(nombreUsuario.trim());
            
            if (usuario != null) {
                // Mostrar contraseña en diálogo personalizado con ojito
                mostrarDialogoConPassword(usuario.getNombre(), usuario.getContrasenia());
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "❌ No se encontró un usuario con ese nombre.",
                    "Usuario no encontrado",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "❌ Error al recuperar la contraseña: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra un diálogo personalizado con la contraseña y botón para mostrar/ocultar
     */
    private void mostrarDialogoConPassword(String nombreUsuario, String password) {
        JDialog dialog = new JDialog(this, "🔑 Recuperación de Contraseña", true);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        
        // Panel principal con GridBagLayout para mejor control
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StyleUtils.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Mensaje de información
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Contraseña recuperada para:<br><b style='font-size: 15px;'>" + nombreUsuario + "</b></div></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(StyleUtils.DARK_TEXT);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        mainPanel.add(infoLabel, gbc);
        
        // Panel contenedor para el campo de contraseña con ancho fijo
        JPanel passwordContainerPanel = new JPanel(new BorderLayout(0, 0));
        passwordContainerPanel.setBackground(StyleUtils.WHITE);
        passwordContainerPanel.setPreferredSize(new Dimension(380, 45));
        
        // Crear campos de contraseña (oculto y visible)
        JPasswordField passwordFieldHidden = StyleUtils.createModernPasswordField(20);
        passwordFieldHidden.setText(password);
        passwordFieldHidden.setEditable(false);
        passwordFieldHidden.setFocusable(false);
        passwordFieldHidden.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTextField passwordFieldVisible = StyleUtils.createModernTextField(20);
        passwordFieldVisible.setText(password);
        passwordFieldVisible.setEditable(false);
        passwordFieldVisible.setFocusable(false);
        passwordFieldVisible.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFieldVisible.setVisible(false);
        
        // Panel con CardLayout para alternar campos
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.setOpaque(false);
        cardPanel.add(passwordFieldHidden, "hidden");
        cardPanel.add(passwordFieldVisible, "visible");
        
        // Botón ojito mejorado
        JButton toggleButton = new JButton("👁️") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2.setColor(StyleUtils.LIGHT_GRAY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        toggleButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        toggleButton.setForeground(StyleUtils.MEDIUM_GRAY);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setPreferredSize(new Dimension(50, 45));
        toggleButton.setToolTipText("Mostrar/Ocultar contraseña");
        toggleButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        final boolean[] isVisible = {false};
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVisible[0] = !isVisible[0];
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                if (isVisible[0]) {
                    cl.show(cardPanel, "visible");
                    toggleButton.setText("🙈");
                } else {
                    cl.show(cardPanel, "hidden");
                    toggleButton.setText("👁️");
                }
            }
        });
        
        passwordContainerPanel.add(cardPanel, BorderLayout.CENTER);
        passwordContainerPanel.add(toggleButton, BorderLayout.EAST);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(passwordContainerPanel, gbc);
        
        // Mensaje de advertencia
        JLabel warningLabel = new JLabel("<html><div style='text-align: center; color: #666;'>" +
            "⚠️ Por favor, anote esta información<br>de forma segura.</div></html>");
        warningLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        warningLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        mainPanel.add(warningLabel, gbc);
        
        // Botón OK
        JButton okButton = StyleUtils.createActionButton("✓ Entendido", "add");
        okButton.setPreferredSize(new Dimension(160, 42));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(okButton, gbc);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    /**
     * Abre la ventana del menú principal
     */
    private void abrirMenuPrincipal() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu.createAndShowMenu();
            }
        });
    }
    
    /**
     * Crea el ícono de la aplicación
     */
    private Image createAppIcon() {
        java.awt.image.BufferedImage icon = new java.awt.image.BufferedImage(32, 32, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = icon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo azul
        g2.setColor(StyleUtils.PRIMARY_BLUE);
        g2.fillRoundRect(0, 0, 32, 32, 8, 8);
        
        // Símbolo de cruz médica
        g2.setColor(StyleUtils.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(16, 8, 16, 24);
        g2.drawLine(8, 16, 24, 16);
        
        g2.dispose();
        return icon;
    }
    
    /**
     * Método principal para iniciar la aplicación con login
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginWindow().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "❌ Error al iniciar la aplicación: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}

