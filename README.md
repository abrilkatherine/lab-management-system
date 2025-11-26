# 🏥 Sistema de Gestión de Laboratorio

Sistema de gestión integral para laboratorios de análisis clínicos que permite administrar pacientes, peticiones, prácticas, resultados y usuarios con soporte multi-sucursal.

## 📋 Requisitos Previos

> 💡 **Para instrucciones detalladas de instalación, consulta [SETUP.md](docs/SETUP.md)**

Antes de ejecutar el proyecto, asegúrate de tener instalado:

### ☕ Java Development Kit (JDK)
- **Versión requerida:** JDK 21 o superior
- **Recomendado:** JDK 21 LTS o JDK 22

#### Verificar instalación de Java:
```bash
java -version
javac -version
```

Deberías ver algo como:
```
openjdk version "21" o "22"
```

#### Descargar JDK:
- **Oracle JDK:** https://www.oracle.com/java/technologies/downloads/
- **OpenJDK:** https://adoptium.net/ (recomendado)

### 🛠️ Make
- **macOS/Linux:** Viene preinstalado
- **Windows:** Instalar [Make for Windows](http://gnuwin32.sourceforge.net/packages/make.htm) o usar WSL

### 📦 Dependencias (ya incluidas)
El proyecto incluye todas las dependencias necesarias en la carpeta `lib/`:
- ✅ **Gson 2.11.0** - Serialización JSON (ya incluido en `lib/gson-2.11.0.jar`)

## 🚀 Instalación y Configuración

### 1️⃣ Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd lab-management-system
```

### 2️⃣ Verificar estructura
Asegúrate de que la carpeta `lib/` contiene:
```bash
ls lib/
# Deberías ver: gson-2.11.0.jar
```

### 3️⃣ Compilar el proyecto
```bash
make build
```

Si ves ✅ **Build completado exitosamente**, ¡todo está listo!

### 4️⃣ Ejecutar la aplicación
```bash
make run
```

## 🚀 Inicio Rápido

```bash
# Compilar y ejecutar el proyecto
make run

# Ejecutar tests
make run-tests

# Ver todos los comandos disponibles
make help
```

## 📋 Características

- ✅ **Gestión de Pacientes** - CRUD completo con validaciones
- ✅ **Gestión de Sucursales** - Con derivación automática de peticiones
- ✅ **Gestión de Peticiones** - Con prácticas y resultados asociados
- ✅ **Gestión de Usuarios** - Con roles y permisos
- ✅ **Interfaz Gráfica** - Desarrollada con Swing
- ✅ **Persistencia** - Almacenamiento en archivos JSON
- ✅ **Reglas de Negocio** - Validaciones de integridad implementadas

## 🛠️ Tecnologías

- **Java 21 LTS** (nivel de lenguaje, requiere JDK 21+)
- **Swing** (Interfaz gráfica)
- **Gson 2.11.0** (Serialización JSON)
- **Make** (Automatización de build)

## 📁 Estructura del Proyecto

```
lab-management-system/
├── src/main/uade/edu/ar/
│   ├── controller/     # Controladores de negocio
│   ├── dao/           # Acceso a datos
│   ├── dto/           # Objetos de transferencia
│   ├── enums/         # Enumeraciones
│   ├── exceptions/    # Excepciones personalizadas
│   ├── factory/       # Fábricas (Factory Pattern)
│   ├── mappers/       # Conversores entre DTO y Model
│   ├── model/         # Entidades del dominio
│   ├── util/          # Utilidades y helpers
│   └── vista/         # Interfaces gráficas (Swing)
├── docs/              # Documentación del proyecto
│   ├── DIAGRAMAS_FLUJO.md
│   ├── PATRONES_GRASP_SOLID.md
│   └── PROPUESTA_NEGOCIO.md
├── lib/               # Dependencias (Gson)
└── out/               # Archivos compilados
```

## 🎯 Comandos Disponibles

| Comando | Descripción |
|---------|-------------|
| `make run` | **Ejecutar el proyecto** |
| `make build` | Build completo |
| `make run-tests` | Ejecutar pruebas |
| `make clean` | Limpiar archivos |
| `make help` | Ver ayuda |

## 📚 Documentación

Este proyecto cuenta con documentación detallada en la carpeta `docs/`:

- **[SETUP.md](docs/SETUP.md)** - Guía rápida de instalación y configuración
- **[PROPUESTA_NEGOCIO.md](docs/PROPUESTA_NEGOCIO.md)** - Descripción del problema de negocio, solución propuesta y funcionalidades
- **[DIAGRAMAS_FLUJO.md](docs/DIAGRAMAS_FLUJO.md)** - Diagramas de flujo completos del sistema (inicialización, autenticación, CRUD, arquitectura)
- **[PATRONES_GRASP_SOLID.md](docs/PATRONES_GRASP_SOLID.md)** - Análisis detallado de patrones GRASP y SOLID implementados con ejemplos de código

## 🏛️ Arquitectura

El proyecto sigue una **arquitectura en capas** con separación clara de responsabilidades:

- **Vista (View)** - Interfaces gráficas en Swing
- **Controlador (Controller)** - Lógica de negocio
- **DAO (Data Access Object)** - Acceso a datos
- **Model** - Entidades del dominio
- **DTO (Data Transfer Object)** - Objetos de transferencia entre capas

### Patrones Implementados

- ✅ **Singleton** - ControllerFactory, SessionManager, Controladores
- ✅ **Factory** - Creación de controladores con dependencias
- ✅ **DAO** - Abstracción de persistencia
- ✅ **DTO** - Separación entre capas
- ✅ **Dependency Injection** - Inyección de dependencias por constructor
- ✅ **Template Method** - GenericDAO para operaciones CRUD

## 🐛 Solución de Problemas

### ❌ Error: "Unable to locate a Java Runtime"
**Solución:** Instala JDK 21 o superior desde [Adoptium](https://adoptium.net/)

### ❌ Error: "command not found: make"
**Solución (Windows):** Instala Make o usa los comandos directamente:
```bash
# En lugar de make build:
javac --release 21 -cp "lib/gson-2.11.0.jar" -d out/classes src/main/uade/edu/ar/**/*.java

# En lugar de make run:
java -cp "out/classes:lib/gson-2.11.0.jar" main.uade.edu.ar.vista.Menu
```

### ❌ Error: compilación falla con errores de sintaxis
**Solución:** Verifica que estás usando JDK 21 o superior con `java -version`

### ❌ Las ventanas no se ven correctamente
**Solución:** Asegúrate de tener un entorno gráfico (X11/macOS/Windows GUI)

## 👥 Equipo

**Proyecto universitario para la materia Paradigma de Objetos - UADE**

## 📝 Notas para Colaboradores

### Estructura de branches
- `main` - Código estable y funcional
- `develop` - Desarrollo activo

### Antes de hacer commit
```bash
make build     # Verificar que compila
make run-tests # Ejecutar tests
```

---

**🎓 Universidad Argentina de la Empresa (UADE) - 2024**
