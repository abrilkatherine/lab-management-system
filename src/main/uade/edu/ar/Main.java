package main.uade.edu.ar;

import main.uade.edu.ar.controller.*;
import main.uade.edu.ar.tests.LabManagementSystemTests;
import main.uade.edu.ar.util.ControllerFactory;

/**
 * Clase principal del sistema.
 * Aplica el patrón Factory para crear controladores con dependencias inyectadas.
 */
public class Main {
    
    // Controladores del sistema (Singleton pattern)
    private static PacienteController pacienteController;
    private static PeticionController peticionController;
    private static SucursalYUsuarioController sucursalYUsuarioController;
    
    public static void main(String[] args) {
        try {
            // Inicializar el sistema
            initializeSystem();
            
            // Ejecutar tests
            LabManagementSystemTests tests = new LabManagementSystemTests();
            tests.runAllTests();
            
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Inicializa todos los controladores del sistema usando ControllerFactory.
     * Aplica el patrón Factory (GRASP) y Dependency Injection (SOLID).
     * 
     * @throws Exception si hay error en la inicialización
     */
    public static void initializeSystem() throws Exception {
        System.out.println("🚀 Inicializando Sistema de Gestión de Laboratorio...");
        
        // Usar Factory para crear controladores con dependencias inyectadas
        ControllerFactory factory = ControllerFactory.getInstance();
        pacienteController = factory.getPacienteController();
        peticionController = factory.getPeticionController();
        sucursalYUsuarioController = factory.getSucursalYUsuarioController();
        
        System.out.println("✅ Sistema inicializado correctamente");
    }
    
    /**
     * Obtiene el controlador de pacientes
     * @return PacienteController
     */
    public static PacienteController getPacienteController() {
        return pacienteController;
    }
    
    /**
     * Obtiene el controlador de peticiones
     * @return PeticionController
     */
    public static PeticionController getPeticionController() {
        return peticionController;
    }
    
    /**
     * Obtiene el controlador de sucursales y usuarios
     * @return SucursalYUsuarioController
     */
    public static SucursalYUsuarioController getSucursalYUsuarioController() {
        return sucursalYUsuarioController;
    }
    
    /**
     * Verifica si el sistema está inicializado
     * @return true si está inicializado, false en caso contrario
     */
    public static boolean isSystemInitialized() {
        return pacienteController != null && 
               peticionController != null && 
               sucursalYUsuarioController != null;
    }
}