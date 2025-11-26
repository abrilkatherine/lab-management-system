# 🔄 Convertir Contraseñas Hasheadas a Texto Plano

Si tienes usuarios con contraseñas hasheadas en el JSON y necesitas convertirlas a texto plano, sigue estas instrucciones.

## ⚠️ Importante

**No puedes "deshashear" una contraseña hasheada.** El hashing es unidireccional. Si tienes contraseñas hasheadas en el JSON, tendrás que:

1. **Saber la contraseña original** para cada usuario
2. **Reemplazarla manualmente** en el JSON con el texto plano
3. O **crear nuevos usuarios** con contraseñas en texto plano

## Opción 1: Editar Manualmente el JSON

1. Abre `src/main/resources/usuarios.json`
2. Para cada usuario, reemplaza la contraseña hasheada con el texto plano
3. Guarda el archivo

**Ejemplo:**
```json
// Antes (hasheada)
{"id":2,"nombre":"Recepcionista","contrasenia":"5459ad42d45ddeb950c7dab6dde07b310f5dfca990fc891d536afff81e7daab0",...}

// Después (texto plano - ejemplo, usa la contraseña real)
{"id":2,"nombre":"Recepcionista","contrasenia":"password123",...}
```

## Opción 2: Crear Nuevos Usuarios

1. Usa la interfaz del sistema para crear nuevos usuarios
2. Las contraseñas se guardarán automáticamente en texto plano
3. Elimina los usuarios antiguos si es necesario

## Opción 3: Script de Conversión (si conoces las contraseñas)

Si conoces las contraseñas originales, puedes usar este código Java:

```java
import main.uade.edu.ar.util.PasswordUtil;

// Verificar qué contraseña genera un hash específico
String hashDelJson = "5459ad42d45ddeb950c7dab6dde07b310f5dfca990fc891d536afff81e7daab0";
String[] posiblesContraseñas = {"password123", "admin", "recepcion", "lab123"};

for (String posible : posiblesContraseñas) {
    String hash = PasswordUtil.hashPassword(posible);
    if (hash.equals(hashDelJson)) {
        System.out.println("Contraseña encontrada: " + posible);
        break;
    }
}
```

## Verificar el Formato Correcto

Un usuario en el JSON debería verse así:

```json
{
  "id": 1,
  "nombre": "admin",
  "contrasenia": "admin123",
  "nacimiento": "Jan 1, 1990, 12:00:00 AM",
  "rol": "ADMINISTRADOR"
}
```

**La contraseña debe ser texto plano, no un hash de 64 caracteres hexadecimales.**

## Después de la Conversión

1. Reinicia la aplicación
2. Intenta hacer login con las contraseñas en texto plano
3. El sistema hasheará automáticamente durante la autenticación

---

**Nota:** Si no conoces las contraseñas originales de los usuarios existentes, tendrás que crear nuevos usuarios o restablecer las contraseñas manualmente.

