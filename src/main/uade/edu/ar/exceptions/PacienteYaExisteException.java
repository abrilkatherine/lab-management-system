package main.uade.edu.ar.exceptions;

/**
 * Excepción personalizada que se lanza cuando se intenta crear un paciente
 * que ya existe en el sistema (basado en el DNI).
 */
public class PacienteYaExisteException extends Exception {
    
    private final int dni;
    
    /**
     * Constructor que recibe el DNI del paciente duplicado
     * @param dni El DNI del paciente que ya existe
     */
    public PacienteYaExisteException(int dni) {
        super("Ya existe un paciente con el DNI: " + dni);
        this.dni = dni;
    }
    
    /**
     * Constructor que recibe el DNI y un mensaje personalizado
     * @param dni El DNI del paciente que ya existe
     * @param mensaje Mensaje personalizado de error
     */
    public PacienteYaExisteException(int dni, String mensaje) {
        super(mensaje);
        this.dni = dni;
    }
    
    /**
     * Obtiene el DNI del paciente duplicado
     * @return El DNI del paciente que ya existe
     */
    public int getDni() {
        return dni;
    }
}
