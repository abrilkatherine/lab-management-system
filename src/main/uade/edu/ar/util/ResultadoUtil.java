package main.uade.edu.ar.util;

import main.uade.edu.ar.enums.TipoResultado;

/**
 * Utilidad para determinar automáticamente el tipo de resultado basado en el valor ingresado.
 * 
 * Reglas de negocio:
 * - RESERVADO: Valores mayores a 150 (requieren retiro presencial)
 * - CRITICO: Valores entre 100 y 150 (requieren atención médica)
 * - NORMAL: Valores menores a 100
 */
public class ResultadoUtil {
    
    private static final double UMBRAL_RESERVADO = 150.0;
    private static final double UMBRAL_CRITICO = 100.0;
    
    /**
     * Determina automáticamente el tipo de resultado basado en el valor ingresado.
     * 
     * @param valor el valor del resultado (puede ser numérico o texto)
     * @return el tipo de resultado correspondiente (NORMAL, CRITICO o RESERVADO)
     */
    public static TipoResultado determinarTipoResultado(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return TipoResultado.NORMAL;
        }
        
        try {
            double valorNumerico = Double.parseDouble(valor.trim());
            
            if (valorNumerico > UMBRAL_RESERVADO) {
                return TipoResultado.RESERVADO;
            } else if (valorNumerico >= UMBRAL_CRITICO) {
                return TipoResultado.CRITICO;
            } else {
                return TipoResultado.NORMAL;
            }
        } catch (NumberFormatException e) {
            // Si no es un número, asumimos que es NORMAL por defecto
            // Podríamos buscar palabras clave si fuera necesario
            return TipoResultado.NORMAL;
        }
    }
    
    /**
     * Obtiene una descripción del tipo de resultado.
     * 
     * @param tipo el tipo de resultado
     * @return descripción del tipo
     */
    public static String getDescripcionTipo(TipoResultado tipo) {
        switch (tipo) {
            case RESERVADO:
                return "Reservado - Debe retirarse presencialmente en sucursal";
            case CRITICO:
                return "Crítico - Requiere atención médica inmediata";
            case NORMAL:
                return "Normal - Dentro de los parámetros esperados";
            default:
                return "Desconocido";
        }
    }
    
    /**
     * Verifica si un valor es numérico.
     * 
     * @param valor el valor a verificar
     * @return true si es numérico, false en caso contrario
     */
    public static boolean esValorNumerico(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(valor.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

