package main.uade.edu.ar.util;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dao.*;

/**
 * Factory para crear controladores con sus dependencias inyectadas.
 * Aplica el patrón Factory (GRASP) y Dependency Injection (SOLID).
 * 
 * Responsabilidades:
 * - Crear instancias de DAOs
 * - Crear instancias de controladores con sus dependencias
 * - Centralizar la lógica de creación de objetos
 */
public class ControllerFactory {
    
    private static ControllerFactory instance;
    
    // DAOs - instancias únicas (podrían ser singletons si es necesario)
    private IPacienteDao pacienteDao;
    private IPeticionDao peticionDao;
    private ISucursalDao sucursalDao;
    private IUsuarioDao usuarioDao;
    
    // Controladores - instancias únicas (singletons)
    private PacienteController pacienteController;
    private PeticionController peticionController;
    private SucursalYUsuarioController sucursalYUsuarioController;
    
    private ControllerFactory() throws Exception {
        initializeDAOs();
    }
    
    /**
     * Obtiene la instancia única del factory (Singleton)
     */
    public static synchronized ControllerFactory getInstance() throws Exception {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }
    
    /**
     * Inicializa todos los DAOs
     * Aplica el principio Creator (GRASP): el Factory es responsable de crear los DAOs
     */
    private void initializeDAOs() throws Exception {
        pacienteDao = new PacienteDao();
        peticionDao = new PeticionDao();
        sucursalDao = new SucursalDao();
        usuarioDao = new UsuarioDao();
    }
    
    /**
     * Obtiene el controlador de pacientes (Singleton)
     * Aplica inyección de dependencias: el controlador recibe sus DAOs por constructor
     */
    public PacienteController getPacienteController() throws Exception {
        if (pacienteController == null) {
            pacienteController = PacienteController.createInstance(pacienteDao, peticionDao);
        }
        return pacienteController;
    }
    
    /**
     * Obtiene el controlador de peticiones (Singleton)
     */
    public PeticionController getPeticionController() throws Exception {
        if (peticionController == null) {
            peticionController = PeticionController.createInstance(peticionDao);
        }
        return peticionController;
    }
    
    /**
     * Obtiene el controlador de sucursales y usuarios (Singleton)
     */
    public SucursalYUsuarioController getSucursalYUsuarioController() throws Exception {
        if (sucursalYUsuarioController == null) {
            sucursalYUsuarioController = SucursalYUsuarioController.createInstance(
                sucursalDao, 
                usuarioDao, 
                peticionDao
            );
        }
        return sucursalYUsuarioController;
    }
    
    /**
     * Método de utilidad para resetear las instancias (útil para testing)
     */
    public void reset() {
        pacienteController = null;
        peticionController = null;
        sucursalYUsuarioController = null;
    }
}

