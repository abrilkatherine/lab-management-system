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

### 4️⃣ Ejecutar la aplicación
```bash
make run

# Ejecutar tests
make run-tests

# Ver todos los comandos disponibles
make help
```

## 📋 Características

- ✅ **Gestión de Pacientes** - CRUD completo con validaciones (DNI único, email, edad, género)
- ✅ **Gestión de Sucursales** - Con derivación automática de peticiones y validaciones de integridad
- ✅ **Gestión de Peticiones** - Con prácticas y resultados asociados
- ✅ **Gestión de Usuarios** - Sistema de roles (ADMINISTRADOR, LABORATORISTA, RECEPCIONISTA) con permisos granulares
- ✅ **Interfaz Gráfica Moderna** - Desarrollada con Swing, diseño intuitivo y responsivo
- ✅ **Persistencia** - Almacenamiento en archivos JSON con Gson
- ✅ **Reglas de Negocio** - Validaciones de integridad referencial implementadas
- ✅ **Control de Acceso** - Sistema de permisos basado en roles
- ✅ **Resultados Críticos** - Detección y visualización automática de resultados que requieren atención inmediata

## 🛠️ Tecnologías

- **Java 21 LTS** (nivel de lenguaje, requiere JDK 21+)
- **Swing** (Interfaz gráfica)
- **Gson 2.11.0** (Serialización JSON)
- **Make** (Automatización de build)

## 📁 Estructura del Proyecto

```
lab-management-system/
├── src/main/uade/edu/ar/
│   ├── controller/    # Controladores de negocio
│   ├── dao/           # Acceso a datos
│   ├── dto/           # Objetos de transferencia
│   ├── enums/         # Enumeraciones
│   ├── exceptions/    # Excepciones personalizadas
│   ├── factory/       # Fábrica (Factory Pattern)
│   ├── mappers/       # Conversores entre DTO y Model
│   ├── model/         # Entidades del dominio
│   ├── util/          # Utilidades y helpers
│   └── vista/         # Interfaces gráficas (Swing)
├── docs/              # Documentación del proyecto
│   ├── DIAGRAMA_CLASES.md
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


### 📄 Documentación Técnica
- **[DIAGRAMA_CLASES.md](docs/DIAGRAMA_CLASES.md)** - Diagrama de clases completo con relaciones
- **[DIAGRAMAS_FLUJO.md](docs/DIAGRAMAS_FLUJO.md)** - Diagramas de flujo del sistema
- **[PATRONES_GRASP_SOLID.md](docs/PATRONES_GRASP_SOLID.md)** - Análisis de patrones y principios SOLID

### 🚀 Guías
- **[SETUP.md](docs/SETUP.md)** - Guía de instalación y configuración
- **[PROPUESTA_NEGOCIO.md](docs/PROPUESTA_NEGOCIO.md)** - Propuesta de negocio detallada

## 🏛️ Arquitectura

El proyecto sigue una **arquitectura en capas** con separación clara de responsabilidades:

- **Vista (View)** - Interfaces gráficas en Swing
- **Controlador (Controller)** - Lógica de negocio
- **DAO (Data Access Object)** - Acceso a datos
- **Model** - Entidades del dominio
- **DTO (Data Transfer Object)** - Objetos de transferencia entre capas

### Patrones Implementados

- ✅ **Singleton** - ControllerFactory, SessionManager, PermissionManager, Controladores
- ✅ **Factory** - Creación de controladores con dependencias
- ✅ **DAO** - Abstracción de persistencia con GenericDAO
- ✅ **DTO** - Separación entre capas con objetos de transferencia
- ✅ **Mapper** - Conversión entre DTO y Model
- ✅ **Dependency Injection** - Inyección de dependencias por constructor
- ✅ **Template Method** - GenericDAO para operaciones CRUD
- ✅ **Strategy** - Validaciones de datos con ValidacionUtil

### Roles y Permisos

| Funcionalidad | RECEPCIONISTA | LABORATORISTA | ADMINISTRADOR |
|--------------|---------------|---------------|---------------|
| Ver Pacientes | ✅ | ✅ | ✅ |
| Agregar/Editar Pacientes | ✅ | ❌ | ✅ |
| Ver Peticiones | ✅ | ✅ | ✅ |
| Crear Peticiones | ✅ | ❌ | ✅ |
| Cargar Resultados | ❌ | ✅ | ✅ |
| Ver Resultados Críticos | ❌ | ✅ | ✅ |
| Gestionar Sucursales | ❌ | ❌ | ✅ |
| Gestionar Usuarios | ❌ | ❌ | ✅ |
| Ser Responsable Técnico | ❌ | ❌ | ✅ |

**🎓 Proyecto universitario para la materia Paradigma de Objetos - Universidad Argentina de la Empresa (UADE) - 2025**
