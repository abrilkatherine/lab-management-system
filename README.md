# 🏥 Sistema de Gestión de Laboratorio

Sistema de gestión para laboratorios que permite administrar pacientes, peticiones, prácticas, resultados y usuarios.

## 🚀 Inicio Rápido

## 📋 Características

- ✅ **Gestión de Pacientes** - CRUD completo con validaciones
- ✅ **Gestión de Sucursales** - Con derivación automática de peticiones
- ✅ **Gestión de Peticiones** - Con prácticas y resultados asociados
- ✅ **Gestión de Usuarios** - Con roles y permisos
- ✅ **Interfaz Gráfica** - Desarrollada con Swing
- ✅ **Persistencia** - Almacenamiento en archivos JSON
- ✅ **Reglas de Negocio** - Validaciones de integridad implementadas
- ✅ **Formato de Fechas Mejorado** - Fechas en formato dd/MM/yyyy HH:mm

## 🛠️ Tecnologías

- **Java 8+**
- **Swing** (Interfaz gráfica)
- **Gson** (Serialización JSON)
- **Make** (Automatización de build)

## 🔄 Mejoras Recientes

- **Formato de Fechas Optimizado** - Las fechas ahora se muestran en formato `dd/MM/yyyy HH:mm` en lugar del formato largo anterior
- **Código Limpio** - Eliminación de métodos obsoletos y optimización del código
- **Tests Actualizados** - Pruebas unitarias mejoradas con manejo de fechas más robusto

## 📁 Estructura del Proyecto

```
lab-management-system/
├── src/main/uade/edu/ar/
│   ├── controller/     # Controladores de negocio
│   ├── dao/           # Acceso a datos
│   ├── dto/           # Objetos de transferencia
│   ├── enums/         # Enumeraciones
│   ├── mappers/       # Conversores
│   ├── model/         # Entidades del dominio
│   ├── util/          # Utilidades
│   └── vista/         # Interfaces gráficas
├── lib/               # Dependencias
├── out/               # Archivos compilados
└── scripts/           # Scripts de automatización
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

- [QUICK_START.md](QUICK_START.md) - Guía de inicio rápido

---

**Proyecto universitario para la materia Paradigma de Objetos**
