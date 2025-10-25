package main.uade.edu.ar.tests;

import main.uade.edu.ar.Main;

public class TestRunner {
    
    public static void main(String[] args) {
        try {
            System.out.println("🧪 Iniciando Test Runner del Sistema de Gestión de Laboratorio...");
            
            // Inicializar el sistema
            Main.initializeSystem();
            
            // Ejecutar tests
            LabManagementSystemTests tests = new LabManagementSystemTests();
            tests.runAllTests();
            
            System.out.println("✅ Test Runner completado exitosamente");
            
        } catch (Exception e) {
            System.err.println("❌ Error en Test Runner: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
