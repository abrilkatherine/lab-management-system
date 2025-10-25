// Clase Menu
package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu {
    private static BarraNavegacion barraNavegacion;
    private static SucursalTodas sucursalTodas;
    private static PacientesTodas pacienteTodas;
    private static UsuariosTodos usuariosTodos;
    private static PeticionesTodas peticionesTodas;
    private static PeticionConResultadosCriticos peticionConResultadoCriticos;
    private static JPanel cardPanel;
    private static CardLayout cardLayout;
    private static SucursalYUsuarioController sucursalYUsuarioController;
    private static PacienteController pacienteController;
    private static PeticionController peticionController;

    public static void main(String[] args) {
        try {
            sucursalYUsuarioController = SucursalYUsuarioController.getInstance(); //Obtenemos la instancia del controller
            pacienteController = PacienteController.getInstance();
            peticionController = PeticionController.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowMenu();
            }
        });
    }

    private static void createAndShowMenu() {
        // Aplicar Look & Feel moderno
        StyleUtils.setModernLookAndFeel();
        
        // Crear una instancia de JFrame para el menú
        JFrame frame = new JFrame("🏥 Sistema de Gestión de Laboratorio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setBackground(StyleUtils.WHITE);

        // Crear una instancia de BarraNavegacion y obtener su panel
        barraNavegacion = new BarraNavegacion();
        JPanel navBarPanel = barraNavegacion.createNavBarPanel();

        // Agregar el panel de barra de navegación al JFrame
        frame.add(navBarPanel, BorderLayout.NORTH);

        // Obtener el cardPanel de BarraNavegacion
        cardPanel = barraNavegacion.getCardPanel();
        
        // Agregar el panel con CardLayout al JFrame
        frame.add(cardPanel, BorderLayout.CENTER);



        // Configurar la ventana con estilo moderno
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(createAppIcon());
        
        // Centrar la ventana
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Método para crear ícono de la aplicación
    private static Image createAppIcon() {
        // Crear un ícono simple con el emoji del hospital
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
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
}
