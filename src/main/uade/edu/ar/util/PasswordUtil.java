package main.uade.edu.ar.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase de utilidad para el manejo seguro de contraseñas.
 * Utiliza SHA-256 para hashear las contraseñas de forma unidireccional.
 * 
 * NOTA: Para producción, se recomienda usar algoritmos más seguros como bcrypt o Argon2,
 * pero SHA-256 es adecuado para este contexto educativo.
 */
public class PasswordUtil {

    // Salt estático para agregar seguridad adicional
    // En producción, cada contraseña debería tener su propio salt único
    private static final String SALT = "LabManagementSystem2024";

    /**
     * Encripta (hashea) una contraseña usando SHA-256 con salt.
     * 
     * @param password La contraseña en texto plano
     * @return El hash hexadecimal de la contraseña, o null si ocurre un error
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + SALT;
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            
            // Convertir bytes a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash almacenado.
     * 
     * @param plainPassword La contraseña en texto plano a verificar
     * @param hashedPassword El hash almacenado para comparar
     * @return true si la contraseña coincide, false en caso contrario
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        String hashOfPlainPassword = hashPassword(plainPassword);
        return hashOfPlainPassword != null && hashOfPlainPassword.equals(hashedPassword);
    }

    /**
     * Verifica si una cadena ya está hasheada (tiene el formato de un hash SHA-256).
     * Un hash SHA-256 siempre tiene 64 caracteres hexadecimales.
     * 
     * @param password La cadena a verificar
     * @return true si parece ser un hash, false si es texto plano
     */
    public static boolean isHashed(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        // Un hash SHA-256 siempre tiene 64 caracteres hexadecimales
        return password.length() == 64 && password.matches("[0-9a-fA-F]{64}");
    }
}

