package main.uade.edu.ar.util;

/**
 * Clase de utilidad para validaciones de datos.
 * Proporciona métodos estáticos para validar DNI y edad según las reglas de negocio.
 */
public class ValidacionUtil {

    // Constantes para validación de DNI (Argentina)
    private static final int DNI_MINIMO = 1000000;  // 7 dígitos
    private static final int DNI_MAXIMO = 99999999; // 8 dígitos

    // Constantes para validación de edad
    private static final int EDAD_MINIMA = 0;
    private static final int EDAD_MAXIMA = 150;

    /**
     * Valida que el DNI sea un número válido.
     * En Argentina, el DNI típicamente tiene entre 7 y 8 dígitos.
     * 
     * @param dniStr El DNI como String
     * @return true si el DNI es válido, false en caso contrario
     */
    public static boolean esDniValido(String dniStr) {
        if (dniStr == null || dniStr.trim().isEmpty()) {
            return false;
        }

        try {
            int dni = Integer.parseInt(dniStr.trim());
            return esDniValido(dni);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que el DNI sea un número válido.
     * En Argentina, el DNI típicamente tiene entre 7 y 8 dígitos.
     * 
     * @param dni El DNI como int
     * @return true si el DNI es válido, false en caso contrario
     */
    public static boolean esDniValido(int dni) {
        return dni >= DNI_MINIMO && dni <= DNI_MAXIMO;
    }

    /**
     * Obtiene el mensaje de error para un DNI inválido.
     * 
     * @return Mensaje de error descriptivo
     */
    public static String getMensajeErrorDni() {
        return "❌ El DNI debe ser un número entre " + DNI_MINIMO + " y " + DNI_MAXIMO + " (7 u 8 dígitos)";
    }

    /**
     * Valida que la edad sea un número válido.
     * 
     * @param edadStr La edad como String
     * @return true si la edad es válida, false en caso contrario
     */
    public static boolean esEdadValida(String edadStr) {
        if (edadStr == null || edadStr.trim().isEmpty()) {
            return false;
        }

        try {
            int edad = Integer.parseInt(edadStr.trim());
            return esEdadValida(edad);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que la edad sea un número válido.
     * 
     * @param edad La edad como int
     * @return true si la edad es válida, false en caso contrario
     */
    public static boolean esEdadValida(int edad) {
        return edad >= EDAD_MINIMA && edad <= EDAD_MAXIMA;
    }

    /**
     * Obtiene el mensaje de error para una edad inválida.
     * 
     * @return Mensaje de error descriptivo
     */
    public static String getMensajeErrorEdad() {
        return "❌ La edad debe ser un número entre " + EDAD_MINIMA + " y " + EDAD_MAXIMA;
    }

    /**
     * Valida y parsea el DNI desde un String.
     * 
     * @param dniStr El DNI como String
     * @return El DNI parseado como int
     * @throws IllegalArgumentException Si el DNI no es válido
     */
    public static int parsearDni(String dniStr) throws IllegalArgumentException {
        if (!esDniValido(dniStr)) {
            throw new IllegalArgumentException(getMensajeErrorDni());
        }
        return Integer.parseInt(dniStr.trim());
    }

    /**
     * Valida y parsea la edad desde un String.
     * 
     * @param edadStr La edad como String
     * @return La edad parseada como int
     * @throws IllegalArgumentException Si la edad no es válida
     */
    public static int parsearEdad(String edadStr) throws IllegalArgumentException {
        if (!esEdadValida(edadStr)) {
            throw new IllegalArgumentException(getMensajeErrorEdad());
        }
        return Integer.parseInt(edadStr.trim());
    }
}

