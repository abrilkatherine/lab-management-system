package main.uade.edu.ar.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.factory.ControllerFactory;
import main.uade.edu.ar.util.StyleUtils;
import main.uade.edu.ar.util.PermissionManager;
import main.uade.edu.ar.util.SessionManager;

public class BarraNavegacion {

    private static SucursalYUsuarioController sucursalYUsuarioController;
    private static PacienteController pacienteController;
    private static PeticionController peticionController;

    private JPanel menuPanel;
    private JPanel buttonPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    // Instancias de las vistas para poder actualizarlas
    private PacientesTodas pacientesTodas;
    private SucursalTodas sucursalTodas;
    private UsuariosTodos usuariosTodos;
    private PeticionesTodas peticionesTodas;
    private PeticionConResultadosCriticos peticionConResultadosCriticos;

    public JPanel createNavBarPanel() {
        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, StyleUtils.PRIMARY_BLUE,
                    0, getHeight(), StyleUtils.PRIMARY_DARK
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                
                g2.dispose();
            }
        };
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(0, 110));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        JLabel logoLabel = new JLabel("🏥 Lab Management System");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoLabel.setForeground(StyleUtils.WHITE);
        topPanel.add(logoLabel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        
        SessionManager sessionManager = SessionManager.getInstance();
        if (sessionManager.haySesionActiva()) {
            String nombreUsuario = sessionManager.getNombreUsuario();
            String rolUsuario = sessionManager.getRolUsuario() != null ? sessionManager.getRolUsuario().toString() : "";
            JLabel userInfoLabel = new JLabel("👤 " + nombreUsuario + " (" + rolUsuario + ")");
            userInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            userInfoLabel.setForeground(StyleUtils.WHITE);
            rightPanel.add(userInfoLabel);
            
            JButton logoutButton = createLogoutButton();
            rightPanel.add(logoutButton);
        }
        
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        menuPanel.add(topPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

        PermissionManager permissionManager = PermissionManager.getInstance();
        JButton homeButton = null;
        JButton sucursalesButton = null;
        JButton peticionesButton = null;
        JButton criticasButton = null;
        JButton usuariosButton = null;

        // Botón Pacientes - visible para todos los roles
        if (permissionManager.puedeVerPacientes()) {
            homeButton = StyleUtils.createNavButton("👥 Pacientes");
            setupButtonActions(homeButton, "👥 Pacientes");
            buttonPanel.add(homeButton);
        }

        // Botón Peticiones - visible para todos los roles
        if (permissionManager.puedeVerPeticiones()) {
            peticionesButton = StyleUtils.createNavButton("📋 Peticiones");
            setupButtonActions(peticionesButton, "📋 Peticiones");
            buttonPanel.add(peticionesButton);
        }

        // Botón Resultados Críticos - solo para LABORATORISTA y ADMINISTRADOR
        if (permissionManager.puedeVerResultadosCriticos()) {
            criticasButton = StyleUtils.createNavButton("⚠️ Críticas");
            setupButtonActions(criticasButton, "⚠️ Críticas");
            buttonPanel.add(criticasButton);
        }

        // Botón Sucursales - solo para ADMINISTRADOR
        if (permissionManager.puedeVerSucursales()) {
            sucursalesButton = StyleUtils.createNavButton("🏢 Sucursales");
            setupButtonActions(sucursalesButton, "🏢 Sucursales");
            buttonPanel.add(sucursalesButton);
        }

        // Botón Usuarios - solo para ADMINISTRADOR
        if (permissionManager.puedeVerUsuarios()) {
            usuariosButton = StyleUtils.createNavButton("👤 Usuarios");
            setupButtonActions(usuariosButton, "👤 Usuarios");
            buttonPanel.add(usuariosButton);
        }

        menuPanel.add(buttonPanel);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        try{
            ControllerFactory factory = ControllerFactory.getInstance();
            sucursalYUsuarioController = factory.getSucursalYUsuarioController();
            peticionController = factory.getPeticionController();
            pacienteController = factory.getPacienteController();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (pacienteController != null) {
            pacientesTodas = new PacientesTodas(pacienteController);
            cardPanel.add(pacientesTodas.createPanel(), "pacientesTodas");
        }
        if (sucursalYUsuarioController != null) {
            sucursalTodas = new SucursalTodas(sucursalYUsuarioController);
            cardPanel.add(sucursalTodas.createPanel(), "sucursalesTodas");
            usuariosTodos = new UsuariosTodos(sucursalYUsuarioController);
            cardPanel.add(usuariosTodos.createPanel(), "usuariosTodos");
        }
        if (peticionController != null && sucursalYUsuarioController != null && pacienteController != null) {
            peticionesTodas = new PeticionesTodas(peticionController, sucursalYUsuarioController, pacienteController);
            cardPanel.add(peticionesTodas.createPanel(), "peticionesTodas");
            peticionConResultadosCriticos = new PeticionConResultadosCriticos(peticionController, sucursalYUsuarioController, pacienteController);
            cardPanel.add(peticionConResultadosCriticos.createPanel(), "PeticionConResultadoCriticos");
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    private void setupButtonActions(JButton button, String text) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.contains("Pacientes")) {
                    if (pacientesTodas != null) {
                        pacientesTodas.actualizarTablaPacientes();
                    }
                    cardLayout.show(cardPanel, "pacientesTodas");
                } else if (text.contains("Sucursales")) {
                    if (sucursalTodas != null) {
                        sucursalTodas.actualizarTablaSucursales();
                    }
                    cardLayout.show(cardPanel, "sucursalesTodas");
                } else if (text.contains("Peticiones") && !text.contains("Críticas")) {
                    if (peticionesTodas != null) {
                        peticionesTodas.actualizarTablaPeticiones();
                    }
                    cardLayout.show(cardPanel, "peticionesTodas");
                } else if (text.contains("Usuarios")) {
                    if (usuariosTodos != null) {
                        usuariosTodos.actualizarTablaUsuarios();
                    }
                    cardLayout.show(cardPanel, "usuariosTodos");
                } else if (text.contains("Críticas")) {
                    if (peticionConResultadosCriticos != null) {
                        peticionConResultadosCriticos.actualizarTablaPeticiones();
                    }
                    cardLayout.show(cardPanel, "PeticionConResultadoCriticos");
                }
            }
        });
    }

    /**
     * Crea el botón de cerrar sesión con estilo moderno
     */
    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("🚪 Cerrar Sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Color de fondo según estado
                if (getModel().isPressed()) {
                    g2.setColor(new Color(220, 53, 69)); // Rojo oscuro al presionar
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(240, 73, 89)); // Rojo más claro en hover
                } else {
                    g2.setColor(new Color(220, 53, 69)); // Rojo normal
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setForeground(StyleUtils.WHITE);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(180, 40));
        logoutButton.setToolTipText("Cerrar sesión y volver al login");
        
        // Acción de cerrar sesión
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        return logoutButton;
    }
    
    /**
     * Cierra la sesión actual y regresa a la pantalla de login
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Cerrar la sesión
            SessionManager.getInstance().cerrarSesion();
            
            // Cerrar la ventana actual
            Window window = SwingUtilities.getWindowAncestor(menuPanel);
            if (window != null) {
                window.dispose();
            }
            
            // Abrir la ventana de login
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginWindow().setVisible(true);
                }
            });
        }
    }
}