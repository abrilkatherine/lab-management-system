// Clase Menu
package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.util.ControllerFactory;
import main.uade.edu.ar.util.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Clase principal de la interfaz gráfica.
 * Usa ControllerFactory para obtener controladores con dependencias inyectadas.
 */
public class Menu {
    private static BarraNavegacion barraNavegacion;
    // Variables para futuras funcionalidades - mantenidas para extensibilidad
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
    // Controladores obtenidos pero no usados directamente en esta clase
    // Se pasan a las vistas a través de BarraNavegacion
    @SuppressWarnings("unused")
    private static SucursalYUsuarioController sucursalYUsuarioController;
    @SuppressWarnings("unused")
    private static PacienteController pacienteController;
    @SuppressWarnings("unused")
    private static PeticionController peticionController;

    public static void main(String[] args) {
        try {
            // Usar Factory para obtener controladores con dependencias inyectadas
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

        // Mostrar la ventana de login en lugar del menú directamente
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    /**
     * Crea y muestra el menú principal.
     * Este método puede ser llamado desde LoginWindow después de una autenticación exitosa.
     */
    public static void createAndShowMenu() {
        // Verificar que hay una sesión activa
        main.uade.edu.ar.util.SessionManager sessionManager = main.uade.edu.ar.util.SessionManager.getInstance();
        if (!sessionManager.haySesionActiva()) {
            // Si no hay sesión, mostrar login
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginWindow().setVisible(true);
                }
            });
            return;
        }
        
        // Aplicar Look & Feel moderno
        StyleUtils.setModernLookAndFeel();
        
        // Crear una instancia de JFrame para el menú
        String titulo = "🏥 Lab Management System";
        if (sessionManager.getUsuarioActual() != null) {
            titulo += " - " + sessionManager.getNombreUsuario() + 
                      " (" + sessionManager.getRolUsuario() + ")";
        }
        JFrame frame = new JFrame(titulo);
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
