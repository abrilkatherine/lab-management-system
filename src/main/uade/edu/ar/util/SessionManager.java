package main.uade.edu.ar.util;

import main.uade.edu.ar.dto.UsuarioDto;

/**
 * Clase para gestionar la sesión del usuario autenticado.
 * Implementa el patrón Singleton para mantener una única sesión activa.
 */
public class SessionManager {
    
    private static SessionManager instance;
    private UsuarioDto usuarioActual;
    
    private SessionManager() {
        // Constructor privado para Singleton
    }
    
    /**
     * Obtiene la instancia única del SessionManager (Singleton)
     * @return La instancia de SessionManager
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Establece el usuario actual de la sesión
     * @param usuario El usuario autenticado
     */
    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
    }
    
    /**
     * Obtiene el usuario actual de la sesión
     * @return El usuario autenticado, o null si no hay sesión activa
     */
    public UsuarioDto getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Verifica si hay una sesión activa
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
    }
    
    /**
     * Obtiene el nombre del usuario actual
     * @return El nombre del usuario, o "Invitado" si no hay sesión
     */
    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombre() : "Invitado";
    }
    
    /**
     * Obtiene el rol del usuario actual
     * @return El rol del usuario, o null si no hay sesión
     */
    public main.uade.edu.ar.enums.Roles getRolUsuario() {
        return usuarioActual != null ? usuarioActual.getRol() : null;
    }
}

