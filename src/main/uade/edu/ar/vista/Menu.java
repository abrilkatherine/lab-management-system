// Clase Menu
package main.uade.edu.ar.vista;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;

import javax.swing.*;
import java.awt.*;

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
        // Crear una instancia de JFrame para el menú
        JFrame frame = new JFrame("Menú");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Crear una instancia de BarraNavegacion y obtener su panel
        barraNavegacion = new BarraNavegacion();
        JPanel navBarPanel = barraNavegacion.createNavBarPanel();

        // Agregar el panel de barra de navegación al JFrame
        frame.add(navBarPanel, BorderLayout.NORTH);

        // Obtener el cardPanel de BarraNavegacion
        cardPanel = barraNavegacion.getCardPanel();
        
        // Agregar el panel con CardLayout al JFrame
        frame.add(cardPanel, BorderLayout.CENTER);



        // Mostrar la ventana
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
