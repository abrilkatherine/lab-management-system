package main.uade.edu.ar.factory;

import main.uade.edu.ar.controller.PacienteController;
import main.uade.edu.ar.controller.PeticionController;
import main.uade.edu.ar.controller.SucursalYUsuarioController;
import main.uade.edu.ar.dao.*;

/**
 * Factory para crear y gestionar instancias de controladores con dependencias inyectadas.
 * Implementa el patrón Singleton y Factory.
 */
public class ControllerFactory {
    
    private static ControllerFactory instance;
    
    private IPacienteDao pacienteDao;
    private IPeticionDao peticionDao;
    private ISucursalDao sucursalDao;
    private IUsuarioDao usuarioDao;
    
    private PacienteController pacienteController;
    private PeticionController peticionController;
    private SucursalYUsuarioController sucursalYUsuarioController;
    
    private ControllerFactory() throws Exception {
        initializeDAOs();
    }
    
    /**
     * Obtiene la instancia única del factory (Singleton).
     * 
     * @return instancia de ControllerFactory
     * @throws Exception si hay error al inicializar
     */
    public static synchronized ControllerFactory getInstance() throws Exception {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }
    
    private void initializeDAOs() throws Exception {
        pacienteDao = new PacienteDao();
        peticionDao = new PeticionDao();
        sucursalDao = new SucursalDao();
        usuarioDao = new UsuarioDao();
    }
    
    /**
     * Obtiene el controlador de pacientes (Singleton).
     * 
     * @return instancia de PacienteController
     * @throws Exception si hay error al crear el controlador
     */
    public PacienteController getPacienteController() throws Exception {
        if (pacienteController == null) {
            pacienteController = PacienteController.createInstance(pacienteDao, peticionDao);
        }
        return pacienteController;
    }
    
    /**
     * Obtiene el controlador de peticiones (Singleton).
     * 
     * @return instancia de PeticionController
     * @throws Exception si hay error al crear el controlador
     */
    public PeticionController getPeticionController() throws Exception {
        if (peticionController == null) {
            peticionController = PeticionController.createInstance(peticionDao);
        }
        return peticionController;
    }
    
    /**
     * Obtiene el controlador de sucursales y usuarios (Singleton).
     * 
     * @return instancia de SucursalYUsuarioController
     * @throws Exception si hay error al crear el controlador
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
    
    public void reset() {
        pacienteController = null;
        peticionController = null;
        sucursalYUsuarioController = null;
    }
}

