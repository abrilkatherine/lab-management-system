package main.uade.edu.ar.util;

import main.uade.edu.ar.enums.Roles;

/**
 * Clase para gestionar los permisos de acceso basados en roles.
 * Centraliza todas las validaciones de permisos del sistema.
 * 
 * Matriz de permisos:
 * - ADMINISTRADOR: Acceso total al sistema
 * - RECEPCIONISTA: Gestión de pacientes y creación de peticiones
 * - LABORATORISTA: Gestión de resultados y visualización de peticiones críticas
 */
public class PermissionManager {
    
    private final SessionManager sessionManager;
    
    /**
     * Constructor privado para implementar Singleton
     */
    private PermissionManager() {
        this.sessionManager = SessionManager.getInstance();
    }
    
    private static class SingletonHelper {
        private static final PermissionManager INSTANCE = new PermissionManager();
    }
    
    /**
     * Obtiene la instancia única del PermissionManager (Singleton)
     * @return La instancia de PermissionManager
     */
    public static PermissionManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    /**
     * Obtiene el rol del usuario actual
     * @return El rol del usuario, o null si no hay sesión activa
     */
    private Roles getRolActual() {
        return sessionManager.getRolUsuario();
    }
    
    // ==================== PERMISOS DE PACIENTES ====================
    
    /**
     * Verifica si el usuario puede ver la lista de pacientes
     * @return true si tiene permiso
     */
    public boolean puedeVerPacientes() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA || rol == Roles.LABORATORISTA;
    }
    
    /**
     * Verifica si el usuario puede agregar pacientes
     * @return true si tiene permiso
     */
    public boolean puedeAgregarPacientes() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede editar pacientes
     * @return true si tiene permiso
     */
    public boolean puedeEditarPacientes() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede eliminar pacientes
     * @return true si tiene permiso
     */
    public boolean puedeEliminarPacientes() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    // ==================== PERMISOS DE PETICIONES ====================
    
    /**
     * Verifica si el usuario puede ver peticiones
     * @return true si tiene permiso
     */
    public boolean puedeVerPeticiones() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA || rol == Roles.LABORATORISTA;
    }
    
    /**
     * Verifica si el usuario puede crear peticiones
     * @return true si tiene permiso
     */
    public boolean puedeCrearPeticiones() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede editar peticiones
     * @return true si tiene permiso
     */
    public boolean puedeEditarPeticiones() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede eliminar peticiones
     * @return true si tiene permiso
     */
    public boolean puedeEliminarPeticiones() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    // ==================== PERMISOS DE PRÁCTICAS Y RESULTADOS ====================
    
    /**
     * Verifica si el usuario puede ver prácticas
     * @return true si tiene permiso
     */
    public boolean puedeVerPracticas() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.LABORATORISTA || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede agregar prácticas a peticiones
     * @return true si tiene permiso
     */
    public boolean puedeAgregarPracticas() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.RECEPCIONISTA;
    }
    
    /**
     * Verifica si el usuario puede cargar/editar resultados de prácticas
     * @return true si tiene permiso
     */
    public boolean puedeCargarResultados() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.LABORATORISTA;
    }
    
    /**
     * Verifica si el usuario puede eliminar prácticas
     * @return true si tiene permiso
     */
    public boolean puedeEliminarPracticas() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    /**
     * Verifica si el usuario puede ver peticiones con resultados críticos
     * @return true si tiene permiso
     */
    public boolean puedeVerResultadosCriticos() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR || rol == Roles.LABORATORISTA;
    }
    
    // ==================== PERMISOS DE SUCURSALES ====================
    
    /**
     * Verifica si el usuario puede ver sucursales
     * @return true si tiene permiso
     */
    public boolean puedeVerSucursales() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    /**
     * Verifica si el usuario puede gestionar sucursales (crear, editar, eliminar)
     * @return true si tiene permiso
     */
    public boolean puedeGestionarSucursales() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    // ==================== PERMISOS DE USUARIOS ====================
    
    /**
     * Verifica si el usuario puede ver usuarios
     * @return true si tiene permiso
     */
    public boolean puedeVerUsuarios() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    /**
     * Verifica si el usuario puede gestionar usuarios (crear, editar, eliminar)
     * @return true si tiene permiso
     */
    public boolean puedeGestionarUsuarios() {
        Roles rol = getRolActual();
        return rol == Roles.ADMINISTRADOR;
    }
    
    // ==================== VALIDACIONES CON EXCEPCIONES ====================
    
    /**
     * Valida que el usuario tenga permiso para una operación
     * @param tienePermiso resultado de la verificación de permiso
     * @param mensaje mensaje de error personalizado
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermiso(boolean tienePermiso, String mensaje) throws SecurityException {
        if (!tienePermiso) {
            throw new SecurityException(mensaje);
        }
    }
    
    /**
     * Valida permiso para agregar pacientes
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoAgregarPacientes() throws SecurityException {
        validarPermiso(puedeAgregarPacientes(), 
            "No tiene permisos para agregar pacientes. Requiere rol: ADMINISTRADOR o RECEPCIONISTA");
    }
    
    /**
     * Valida permiso para editar pacientes
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoEditarPacientes() throws SecurityException {
        validarPermiso(puedeEditarPacientes(), 
            "No tiene permisos para editar pacientes. Requiere rol: ADMINISTRADOR o RECEPCIONISTA");
    }
    
    /**
     * Valida permiso para eliminar pacientes
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoEliminarPacientes() throws SecurityException {
        validarPermiso(puedeEliminarPacientes(), 
            "No tiene permisos para eliminar pacientes. Requiere rol: ADMINISTRADOR");
    }
    
    /**
     * Valida permiso para cargar resultados
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoCargarResultados() throws SecurityException {
        validarPermiso(puedeCargarResultados(), 
            "No tiene permisos para cargar resultados. Requiere rol: ADMINISTRADOR o LABORATORISTA");
    }
    
    /**
     * Valida permiso para gestionar sucursales
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoGestionarSucursales() throws SecurityException {
        validarPermiso(puedeGestionarSucursales(), 
            "No tiene permisos para gestionar sucursales. Requiere rol: ADMINISTRADOR");
    }
    
    /**
     * Valida permiso para gestionar usuarios
     * @throws SecurityException si no tiene permiso
     */
    public void validarPermisoGestionarUsuarios() throws SecurityException {
        validarPermiso(puedeGestionarUsuarios(), 
            "No tiene permisos para gestionar usuarios. Requiere rol: ADMINISTRADOR");
    }
    
    /**
     * Obtiene el nombre del rol actual (para mostrar en UI)
     * @return Nombre del rol o "Sin sesión"
     */
    public String getNombreRolActual() {
        Roles rol = getRolActual();
        if (rol == null) return "Sin sesión";
        
        switch (rol) {
            case ADMINISTRADOR:
                return "Administrador";
            case RECEPCIONISTA:
                return "Recepcionista";
            case LABORATORISTA:
                return "Laboratorista";
            default:
                return "Desconocido";
        }
    }
}

