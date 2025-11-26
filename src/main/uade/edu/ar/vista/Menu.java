package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.factory.ControllerFactory;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {
    private static BarraNavegacion barraNavegacion;
    @SuppressWarnings("unused")
    private static SucursalTodas sucursalTodas;
    @SuppressWarnings("unused")
    private static PacientesTodas pacienteTodas;
    @SuppressWarnings("unused")
    private static UsuariosTodos usuariosTodos;
    @SuppressWarnings("unused")
    private static PeticionesTodas peticionesTodas;
    @SuppressWarnings("unused")
    private static PeticionConResultadosCriticos peticionConResultadoCriticos;
    private static JPanel cardPanel;
    @SuppressWarnings("unused")
    private static CardLayout cardLayout;
    @SuppressWarnings("unused")
    private static SucursalYUsuarioController sucursalYUsuarioController;
    @SuppressWarnings("unused")
    private static PacienteController pacienteController;
    @SuppressWarnings("unused")
    private static PeticionController peticionController;

    public static void main(String[] args) {
        try {
            ControllerFactory factory = ControllerFactory.getInstance();
            sucursalYUsuarioController = factory.getSucursalYUsuarioController();
            pacienteController = factory.getPacienteController();
            peticionController = factory.getPeticionController();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "❌ Error al inicializar el sistema: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    public static void createAndShowMenu() {
        main.uade.edu.ar.util.SessionManager sessionManager = main.uade.edu.ar.util.SessionManager.getInstance();
        if (!sessionManager.haySesionActiva()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginWindow().setVisible(true);
                }
            });
            return;
        }
        
        StyleUtils.setModernLookAndFeel();
        
        String titulo = "🏥 Lab Management System";
        if (sessionManager.getUsuarioActual() != null) {
            titulo += " - " + sessionManager.getNombreUsuario() + 
                      " (" + sessionManager.getRolUsuario() + ")";
        }
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setBackground(StyleUtils.WHITE);

        barraNavegacion = new BarraNavegacion();
        JPanel navBarPanel = barraNavegacion.createNavBarPanel();
        frame.add(navBarPanel, BorderLayout.NORTH);

        cardPanel = barraNavegacion.getCardPanel();
        frame.add(cardPanel, BorderLayout.CENTER);

        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(createAppIcon());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static Image createAppIcon() {
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = icon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(StyleUtils.PRIMARY_BLUE);
        g2.fillRoundRect(0, 0, 32, 32, 8, 8);
        
        g2.setColor(StyleUtils.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(16, 8, 16, 24);
        g2.drawLine(8, 16, 24, 16);
        
        g2.dispose();
        return icon;
    }
}
