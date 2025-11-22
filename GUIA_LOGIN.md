# 🔐 Guía de Uso del Sistema de Login

## Descripción

Se ha implementado un sistema de autenticación completo para el Sistema de Gestión de Laboratorio. Los usuarios ahora deben iniciar sesión antes de acceder al sistema principal.

## Características Implementadas

### ✅ Interfaz de Login
- Ventana de login moderna y consistente con el diseño del sistema
- Campos para usuario y contraseña
- Validación de campos requeridos
- Mensajes de error claros y descriptivos
- Soporte para presionar Enter en los campos para iniciar sesión

### ✅ Autenticación Segura
- Las contraseñas se almacenan hasheadas usando SHA-256
- Verificación segura de credenciales
- Protección contra autenticación sin credenciales válidas

### ✅ Gestión de Sesión
- Sistema de sesión que mantiene al usuario autenticado
- Información del usuario visible en el título de la ventana principal
- Cierre de sesión al cerrar la aplicación

## Cómo Usar

### 1. Iniciar la Aplicación

Ejecuta el sistema como siempre:

```bash
make run
```

O directamente:

```bash
java -cp "out/classes:lib/gson-2.10.1.jar" main.uade.edu.ar.vista.Menu
```

### 2. Ventana de Login

Al iniciar la aplicación, verás una ventana de login con:
- **Campo de Usuario**: Ingresa el nombre del usuario
- **Campo de Contraseña**: Ingresa la contraseña (se oculta mientras escribes)
- **Botón "Iniciar Sesión"**: Para autenticarte
- **Botón "Cancelar"**: Para salir de la aplicación

### 3. Autenticación

1. Ingresa tu nombre de usuario en el campo correspondiente
2. Ingresa tu contraseña
3. Presiona "Iniciar Sesión" o presiona Enter
4. Si las credenciales son correctas, se abrirá el menú principal
5. Si las credenciales son incorrectas, verás un mensaje de error

### 4. Menú Principal

Una vez autenticado:
- El menú principal mostrará tu nombre y rol en el título
- Tendrás acceso completo a todas las funcionalidades del sistema
- La sesión permanecerá activa hasta que cierres la aplicación

## Estructura de Usuarios

Los usuarios se almacenan en `src/main/resources/usuarios.json` con el siguiente formato:

```json
{
  "id": 1,
  "nombre": "nombreUsuario",
  "contrasenia": "textoPlano",
  "nacimiento": "2024-01-01",
  "rol": "ADMINISTRADOR"
}
```

**Nota importante:** Las contraseñas se guardan en **texto plano** en el JSON. El hashing se realiza solo durante la autenticación en la interfaz para comparación.

### Roles Disponibles
- `RECEPCION`
- `LABORTISTA`
- `ADMINISTRADOR`

## Crear un Nuevo Usuario

Para crear un nuevo usuario:

1. Usa la interfaz del sistema (Agregar Usuario)
2. La contraseña se guardará en **texto plano** en el JSON
3. Durante el login, la contraseña se hasheará automáticamente para comparación

**Nota:** No necesitas hashear manualmente las contraseñas. El sistema las guarda en texto plano y las hashea solo durante la autenticación.

## Componentes Creados

### 1. `LoginWindow.java`
Ventana de login con Swing que:
- Muestra campos de usuario y contraseña
- Valida las credenciales
- Abre el menú principal tras autenticación exitosa

### 2. `SessionManager.java`
Gestiona la sesión del usuario:
- Almacena el usuario autenticado
- Proporciona métodos para acceder a la información de la sesión
- Implementa patrón Singleton

### 3. Métodos en `SucursalYUsuarioController`
- `autenticarUsuario(String nombre, String contrasenia)`: Autentica un usuario
- `getUsuarioPorNombre(String nombre)`: Busca un usuario por nombre

## Seguridad

### ✅ Implementado
- Hashing de contraseñas durante la autenticación (SHA-256 con salt)
- Validación de credenciales
- Sesión única por instancia de aplicación
- Las contraseñas se hashean solo en la interfaz durante el login

### ⚠️ Nota de Seguridad
Las contraseñas se almacenan en **texto plano** en el archivo JSON. Esto es adecuado para entornos de desarrollo y pruebas, pero para producción se recomienda:
- Implementar encriptación del archivo JSON
- O migrar a una base de datos con contraseñas hasheadas
- Usar algoritmos más seguros como BCrypt

### 🔒 Recomendaciones Futuras
- Implementar timeout de sesión
- Agregar intentos máximos de login
- Implementar bloqueo de cuenta tras múltiples intentos fallidos
- Migrar contraseñas a formato hasheado en el JSON (si se requiere)

## Solución de Problemas

### "Usuario o contraseña incorrectos"
- Verifica que el nombre de usuario sea exacto (case-insensitive)
- Verifica que la contraseña sea correcta (debe coincidir exactamente con la del JSON)
- Asegúrate de que el usuario exista en `usuarios.json`
- **Importante:** La contraseña en el JSON debe estar en texto plano, no hasheada

### "Error al inicializar el sistema"
- Verifica que los archivos JSON estén en `src/main/resources/`
- Verifica que los controladores se inicialicen correctamente
- Revisa los logs para más detalles

### La ventana de login no aparece
- Verifica que `LoginWindow.java` esté compilado
- Ejecuta `make clean` y luego `make compile`
- Verifica que no haya errores de compilación

## Notas Técnicas

- El login es el punto de entrada principal de la aplicación
- `Menu.main()` ahora redirige automáticamente a `LoginWindow`
- La sesión se mantiene en memoria durante la ejecución
- Al cerrar la aplicación, la sesión se pierde (no hay persistencia de sesión)

---

**¿Necesitas ayuda?** Revisa los logs de la aplicación o contacta al equipo de desarrollo.

