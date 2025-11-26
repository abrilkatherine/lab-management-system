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

public class BarraNavegacion {

    private static SucursalYUsuarioController sucursalYUsuarioController;
    private static PacienteController pacienteController;
    private static PeticionController peticionController;

    private JPanel menuPanel;
    private JPanel buttonPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("🏥 Lab Management System");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoLabel.setForeground(StyleUtils.WHITE);
        topPanel.add(logoLabel);
        
        menuPanel.add(topPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

        JButton homeButton = StyleUtils.createNavButton("👥 Pacientes");
        JButton sucursalesButton = StyleUtils.createNavButton("🏢 Sucursales");
        JButton peticionesButton = StyleUtils.createNavButton("📋 Peticiones");
        JButton criticasButton = StyleUtils.createNavButton("⚠️ Críticas");
        JButton usuariosButton = StyleUtils.createNavButton("👤 Usuarios");

        setupButtonActions(homeButton, "👥 Pacientes");
        setupButtonActions(sucursalesButton, "🏢 Sucursales");
        setupButtonActions(peticionesButton, "📋 Peticiones");
        setupButtonActions(criticasButton, "⚠️ Críticas");
        setupButtonActions(usuariosButton, "👤 Usuarios");

        buttonPanel.add(homeButton);
        buttonPanel.add(sucursalesButton);
        buttonPanel.add(peticionesButton);
        buttonPanel.add(criticasButton);
        buttonPanel.add(usuariosButton);

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
            cardPanel.add(new PacientesTodas(pacienteController).createPanel(), "pacientesTodas");
        }
        if (sucursalYUsuarioController != null) {
            cardPanel.add(new SucursalTodas(sucursalYUsuarioController).createPanel(), "sucursalesTodas");
            cardPanel.add(new UsuariosTodos(sucursalYUsuarioController).createPanel(), "usuariosTodos");
        }
        if (peticionController != null && sucursalYUsuarioController != null && pacienteController != null) {
            cardPanel.add(new PeticionesTodas(peticionController, sucursalYUsuarioController, pacienteController).createPanel(), "peticionesTodas");
            cardPanel.add(new PeticionConResultadosCriticos(peticionController, sucursalYUsuarioController, pacienteController).createPanel(), "PeticionConResultadoCriticos");
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
                    cardLayout.show(cardPanel, "pacientesTodas");
                } else if (text.contains("Sucursales")) {
                    cardLayout.show(cardPanel, "sucursalesTodas");
                } else if (text.contains("Peticiones") && !text.contains("Críticas")) {
                    cardLayout.show(cardPanel, "peticionesTodas");
                } else if (text.contains("Usuarios")) {
                    cardLayout.show(cardPanel, "usuariosTodos");
                } else if (text.contains("Críticas")) {
                    cardLayout.show(cardPanel, "PeticionConResultadoCriticos");
                }
            }
        });
    }
}