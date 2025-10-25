# 🚀 Comandos para el Sistema de Gestión de Laboratorio

Este documento describe los comandos disponibles para compilar, ejecutar y gestionar el proyecto.

## 📋 Comandos Disponibles

### 🔨 Compilación

#### `./compile.sh`
Compila todo el proyecto Java con las dependencias necesarias.

```bash
./compile.sh
```

**Qué hace:**
- Limpia compilaciones anteriores
- Compila todas las clases Java
- Incluye la librería Gson en el classpath
- Genera las clases en `out/classes/`

### 🚀 Ejecución

#### `./run.sh`
Ejecuta la aplicación con interfaz gráfica (Swing).

```bash
./run.sh
```

**Qué hace:**
- Verifica que el proyecto esté compilado
- Si no está compilado, lo compila automáticamente
- Ejecuta la interfaz gráfica principal (`Menu.java`)

#### `./run-tests.sh`
Ejecuta las pruebas del sistema (Main.java).

```bash
./run-tests.sh
```

**Qué hace:**
- Verifica que el proyecto esté compilado
- Ejecuta las pruebas de funcionalidad
- Muestra resultados de las pruebas en consola

### 🧹 Limpieza

#### `./clean.sh`
Limpia archivos compilados y temporales.

```bash
./clean.sh
```

**Qué hace:**
- Elimina todas las clases compiladas
- Limpia archivos temporales del sistema
- Prepara el proyecto para una compilación limpia

### 🏗️ Build Completo

#### `./build.sh`
Ejecuta un build completo del proyecto.

```bash
./build.sh
```

**Qué hace:**
1. Limpia el proyecto (`./clean.sh`)
2. Compila el proyecto (`./compile.sh`)
3. Verifica que todas las clases principales estén presentes
4. Reporta el estado del build

## 🎯 Flujo de Trabajo Recomendado

### Para Desarrollo Diario:
```bash
# 1. Limpiar y compilar
./build.sh

# 2. Ejecutar aplicación
./run.sh
```

### Para Pruebas:
```bash
# 1. Compilar
./compile.sh

# 2. Ejecutar pruebas
./run-tests.sh
```

### Para Limpiar Todo:
```bash
./clean.sh
```

## 📁 Estructura de Archivos

```
lab-management-system/
├── compile.sh          # Script de compilación
├── run.sh             # Script de ejecución GUI
├── run-tests.sh       # Script de ejecución de pruebas
├── clean.sh           # Script de limpieza
├── build.sh           # Script de build completo
├── COMANDOS.md        # Esta documentación
├── lib/
│   └── gson-2.10.1.jar
├── out/
│   └── classes/       # Clases compiladas
└── src/
    └── main/
        └── uade/edu/ar/
            ├── controller/
            ├── dao/
            ├── dto/
            ├── enums/
            ├── mappers/
            ├── model/
            ├── util/
            └── vista/
```

## ⚠️ Requisitos

- **Java JDK 8+** instalado
- **Bash** (disponible en macOS/Linux)
- **Permisos de ejecución** en los scripts

## 🔧 Solución de Problemas

### Error: "Permission denied"
```bash
chmod +x *.sh
```

### Error: "Java not found"
Verificar que Java esté instalado:
```bash
java -version
javac -version
```

### Error: "Class not found"
Limpiar y recompilar:
```bash
./clean.sh
./compile.sh
```

### Error: "Gson not found"
Verificar que el archivo `lib/gson-2.10.1.jar` existe.

## 📞 Soporte

Si encuentras problemas con los comandos:

1. Verifica que todos los scripts tengan permisos de ejecución
2. Asegúrate de que Java esté instalado y en el PATH
3. Verifica que la librería Gson esté en `lib/gson-2.10.1.jar`
4. Ejecuta `./clean.sh` y luego `./build.sh` para un build limpio
