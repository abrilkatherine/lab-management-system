package main.uade.edu.ar;

import main.uade.edu.ar.controller.*;
import main.uade.edu.ar.tests.LabManagementSystemTests;
import main.uade.edu.ar.factory.ControllerFactory;

/**
 * Clase principal del sistema de gestión de laboratorio.
 * Inicializa los controladores y ejecuta los tests del sistema.
 */
public class Main {
    
    private static PacienteController pacienteController;
    private static PeticionController peticionController;
    private static SucursalYUsuarioController sucursalYUsuarioController;
    
    public static void main(String[] args) {
        try {
            initializeSystem();
            LabManagementSystemTests tests = new LabManagementSystemTests();
            tests.runAllTests();
            
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Inicializa el sistema creando los controladores mediante ControllerFactory.
     * 
     * @throws Exception si hay error en la inicialización
     */
    public static void initializeSystem() throws Exception {
        System.out.println("🚀 Inicializando Sistema de Gestión de Laboratorio...");
        ControllerFactory factory = ControllerFactory.getInstance();
        pacienteController = factory.getPacienteController();
        peticionController = factory.getPeticionController();
        sucursalYUsuarioController = factory.getSucursalYUsuarioController();
        
        System.out.println("✅ Sistema inicializado correctamente");
    }
    
    public static PacienteController getPacienteController() {
        return pacienteController;
    }
    
    public static PeticionController getPeticionController() {
        return peticionController;
    }
    
    public static SucursalYUsuarioController getSucursalYUsuarioController() {
        return sucursalYUsuarioController;
    }
    
    public static boolean isSystemInitialized() {
        return pacienteController != null && 
               peticionController != null && 
               sucursalYUsuarioController != null;
    }
}