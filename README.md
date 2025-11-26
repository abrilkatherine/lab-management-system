# 🏥 Sistema de Gestión de Laboratorio

Sistema de gestión integral para laboratorios de análisis clínicos que permite administrar pacientes, peticiones, prácticas, resultados y usuarios con soporte multi-sucursal.

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

- **Java 8+**
- **Swing** (Interfaz gráfica)
- **Gson** (Serialización JSON)
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

---

**Proyecto universitario para la materia Paradigma de Objetos - UADE**
