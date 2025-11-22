# 👥 Usuarios de Prueba para Login

He actualizado el archivo `usuarios.json` con usuarios de prueba que tienen contraseñas en texto plano. Puedes usar estos usuarios para probar el sistema de login:

## 🔐 Credenciales de Prueba

### Administrador
- **Usuario:** `admin`
- **Contraseña:** `admin123`
- **Rol:** ADMINISTRADOR

### Recepcionista
- **Usuario:** `Recepcionista`
- **Contraseña:** `recepcion123`
- **Rol:** RECEPCION

### Laboratorista
- **Usuario:** `Laboratorista`
- **Contraseña:** `lab123`
- **Rol:** LABORTISTA

## 🚀 Cómo Probar

1. **Compila el proyecto:**
   ```bash
   make compile
   ```

2. **Ejecuta la aplicación:**
   ```bash
   make run
   ```

3. **Inicia sesión** con cualquiera de los usuarios de arriba

4. **Verifica** que:
   - El login funcione correctamente
   - Se muestre tu nombre y rol en el título de la ventana principal
   - Tengas acceso a todas las funcionalidades del sistema

## 📝 Notas

- Las contraseñas están en **texto plano** en el JSON
- Durante el login, las contraseñas se hashean automáticamente para comparación
- Puedes crear más usuarios desde la interfaz del sistema
- Los nuevos usuarios también se guardarán con contraseñas en texto plano

## ⚠️ Seguridad

Estas son contraseñas de prueba. En un entorno de producción:
- Usa contraseñas más seguras
- Considera hashear las contraseñas en el JSON
- O migra a una base de datos con mejor seguridad

---

¡Listo para probar! 🎉

