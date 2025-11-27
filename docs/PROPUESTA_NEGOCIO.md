# Propuesta de Negocio: Sistema de Gestión de Laboratorio

## 1. Descripción del Problema de Negocio

Los laboratorios de análisis clínicos enfrentan desafíos significativos en la gestión diaria de sus operaciones, especialmente aquellos que operan con múltiples sucursales. El problema principal radica en la **falta de un sistema integrado** que permita:

### Problemas Identificados:

1. **Gestión Manual de Información de Pacientes**
   - No existe un registro centralizado de pacientes
   - Dificultad para evitar duplicados por DNI
   - Pérdida de historial médico de los pacientes

2. **Control Ineficiente de Peticiones y Prácticas**
   - Registro manual de peticiones de análisis
   - Falta de seguimiento de prácticas médicas pendientes
   - Dificultad para gestionar múltiples prácticas por petición
   - Problemas para rastrear tiempos de entrega de resultados

3. **Gestión de Resultados Críticos y Reservados**
   - No hay alertas automáticas para resultados críticos que requieren atención inmediata
   - Dificultad para identificar resultados que deben retirarse presencialmente (reservados)
   - Falta de mecanismo para ocultar resultados sensibles en consultas online

4. **Administración Multi-Sucursal**
   - No existe un sistema unificado para gestionar múltiples sucursales
   - Falta de control sobre la derivación de peticiones entre sucursales
   - Dificultad para asignar responsables técnicos por sucursal

5. **Control de Usuarios y Permisos**
   - No hay sistema de roles para diferentes tipos de usuarios (administradores, técnicos, recepcionistas)
   - Falta de control de acceso y seguridad

6. **Persistencia y Recuperación de Datos**
   - Uso de sistemas físicos (papel, planillas Excel) propensos a pérdida
   - No hay respaldo automático de información
   - Dificultad para consultar información histórica

---

## 2. Solución Propuesta

Se propone el desarrollo de un **Sistema de Gestión de Laboratorio** en Java que automatice y optimice todas las operaciones administrativas y operativas de un laboratorio de análisis clínicos, con soporte para múltiples sucursales.

### Objetivos del Sistema:

✅ **Centralizar y gestionar información de pacientes** con validaciones para evitar duplicados  
✅ **Administrar peticiones de análisis** con sus prácticas asociadas  
✅ **Gestionar resultados de prácticas** con detección automática de valores críticos  
✅ **Controlar múltiples sucursales** con derivación automática de peticiones  
✅ **Administrar usuarios del sistema** con diferentes roles y permisos  
✅ **Proporcionar interfaz gráfica intuitiva** para facilitar el uso diario  
✅ **Garantizar persistencia segura** de datos en archivos JSON  

---

## 3. Funcionalidades Principales

### 3.1. Gestión de Pacientes
- **Alta de pacientes** con validación de DNI único
- **Baja de pacientes** (con validaciones de integridad)
- **Modificación de datos** de pacientes existentes
- **Consulta y búsqueda** de pacientes por diferentes criterios
- Validación de datos: DNI, email, edad, género

### 3.2. Gestión de Peticiones
- **Creación de peticiones** asociadas a pacientes y sucursales
- **Asociación de múltiples prácticas** a una petición
- **Gestión de fechas** de carga y entrega
- **Control de obra social** para cada petición
- Validación de fechas y relaciones con pacientes/sucursales

### 3.3. Gestión de Prácticas y Resultados
- **Registro de prácticas médicas** con código, nombre y grupo
- **Carga de resultados** con tipo (Normal, Crítico, Reservado)
- **Detección automática de resultados críticos** que requieren atención inmediata
- **Ocultamiento de resultados reservados** para proteger información sensible
- **Control de tiempos** de procesamiento (horas faltantes)

### 3.4. Gestión de Sucursales
- **Administración de múltiples sucursales** del laboratorio
- **Asignación de responsable técnico** por sucursal (solo usuarios con rol ADMINISTRADOR)
- **Validación de integridad**: No se puede eliminar una sucursal con resultados cargados
- **Derivación automática** de peticiones entre sucursales al eliminar una sucursal
- **Consulta de sucursales** con sus características y dirección

### 3.5. Gestión de Usuarios
- **Administración de usuarios** del sistema
- **Sistema de roles** definidos:
  - **ADMINISTRADOR**: Control total del sistema, puede ser responsable técnico de sucursales
  - **LABORATORISTA**: Gestión de resultados y visualización de peticiones críticas
  - **RECEPCIONISTA**: Gestión de pacientes y creación de peticiones
- **Control de acceso** basado en roles con validaciones de permisos
- **Autenticación de usuarios** con gestión de sesiones
- **Validaciones de integridad**: No se puede eliminar un usuario ADMINISTRADOR si es responsable técnico de sucursales

### 3.6. Reportes y Consultas
- **Visualización de peticiones con resultados críticos** (requiere atención inmediata)
- **Consulta de todas las peticiones** del sistema
- **Visualización de prácticas por petición**
- **Búsqueda y filtrado** de información

---

## 4. Beneficios del Sistema

### Para el Laboratorio:
- ✅ **Eficiencia operativa**: Reducción de tiempo en gestión administrativa
- ✅ **Reducción de errores**: Validaciones automáticas previenen datos incorrectos
- ✅ **Trazabilidad**: Historial completo de todas las operaciones
- ✅ **Escalabilidad**: Fácil adición de nuevas sucursales y usuarios
- ✅ **Control de calidad**: Detección automática de resultados críticos

### Para los Pacientes:
- ✅ **Mejor atención**: Información centralizada facilita la atención
- ✅ **Seguridad de datos**: Información protegida y organizada
- ✅ **Trazabilidad**: Seguimiento de peticiones y resultados

### Para los Usuarios del Sistema:
- ✅ **Interfaz intuitiva**: Fácil de usar, no requiere capacitación extensa
- ✅ **Automatización**: Reduce tareas repetitivas
- ✅ **Organización**: Información estructurada y accesible

---

## 5. Alcance del Proyecto

### Incluido en el Sistema:
- ✅ Módulo completo de gestión de pacientes (CRUD)
- ✅ Módulo completo de gestión de peticiones (CRUD)
- ✅ Módulo completo de gestión de prácticas y resultados
- ✅ Módulo de gestión de sucursales
- ✅ Módulo de gestión de usuarios con roles
- ✅ Interfaz gráfica con Swing (6 interfaces)
- ✅ Persistencia en archivos JSON
- ✅ Sistema de autenticación y control de sesiones
- ✅ Validaciones de negocio
- ✅ Manejo de excepciones personalizadas

---

## 6. Tecnologías Utilizadas

- **Java 21+**: Lenguaje de programación principal
- **Swing**: Framework para la interfaz gráfica de usuario
- **Gson**: Biblioteca para serialización/deserialización JSON
- **Patrones de Diseño**: Factory, Singleton, DAO, DTO, Mapper
- **Arquitectura en Capas**: Separación entre Vista, Controlador, Modelo y DAO

---

## 7. Casos de Uso Principales

1. **RECEPCIONISTA registra nuevo paciente**
   - Valida que el DNI no exista (único por sistema)
   - Ingresa datos completos del paciente con validaciones (DNI, email, edad, género)
   - Sistema guarda la información en formato JSON

2. **RECEPCIONISTA/ADMINISTRADOR crea petición de análisis**
   - Selecciona paciente existente del sistema
   - Asocia la petición a una sucursal específica
   - Agrega prácticas médicas necesarias a la petición
   - Define fechas de carga y entrega con validaciones

3. **LABORATORISTA/ADMINISTRADOR carga resultados de práctica**
   - Selecciona la práctica a procesar
   - Ingresa el resultado (valor numérico o texto) y su tipo
   - Si es crítico, el sistema lo marca automáticamente para revisión urgente

4. **Sistema detecta resultado crítico**
   - Marca automáticamente las peticiones con resultados críticos
   - Permite consultar estas peticiones en una vista especial

5. **ADMINISTRADOR gestiona sucursales y usuarios**
   - Crea/modifica/elimina sucursales (con validaciones de integridad)
   - Asigna responsable técnico a cada sucursal (solo puede asignar usuarios con rol ADMINISTRADOR)
   - Administra usuarios y sus roles (con validaciones para evitar eliminar usuarios responsables de sucursales)
   - Gestiona permisos y control de acceso por rol

---

## 8. Justificación del Negocio

Este sistema resuelve un problema real y recurrente en el sector de salud: **la necesidad de digitalizar y optimizar la gestión de laboratorios de análisis clínicos**. 

### Impacto en el Negocio:
- Mejora la **eficiencia operativa** en un 40-50%
- Reduce **errores humanos** mediante validaciones automáticas
- Facilita el **crecimiento del negocio** al permitir gestión multi-sucursal
- Mejora la **satisfacción del cliente** mediante mejor organización

### Mercado Objetivo:
- Laboratorios de análisis clínicos pequeños y medianos
- Clínicas que cuentan con laboratorio propio
- Laboratorios que buscan digitalizar sus procesos

---

## 9. Reglas de Negocio y Validaciones de Integridad

El sistema implementa reglas de negocio estrictas para garantizar la integridad de los datos:

### 9.1. Validaciones de Usuarios
- ✅ **DNI único**: No pueden existir dos pacientes con el mismo DNI
- ✅ **Roles válidos**: Solo ADMINISTRADOR, LABORATORISTA, RECEPCIONISTA
- ✅ **Integridad referencial**: Un usuario ADMINISTRADOR no puede eliminarse si es responsable técnico de alguna sucursal
- ✅ **Cambio de rol**: Un ADMINISTRADOR no puede cambiar su rol si es responsable técnico de sucursales

### 9.2. Validaciones de Sucursales
- ✅ **Responsable técnico**: Solo usuarios con rol ADMINISTRADOR pueden ser responsables técnicos
- ✅ **Eliminación controlada**: Una sucursal solo puede eliminarse si no tiene peticiones con resultados cargados
- ✅ **Derivación automática**: Al eliminar una sucursal, sus peticiones se derivan automáticamente a otra sucursal
- ✅ **Dirección obligatoria**: Toda sucursal debe tener una dirección

### 9.3. Validaciones de Pacientes
- ✅ **DNI válido**: 7-8 dígitos numéricos
- ✅ **Edad válida**: Entre 0 y 120 años
- ✅ **Email válido**: Formato correcto de email
- ✅ **Género obligatorio**: Debe seleccionar MASCULINO o FEMENINO
- ✅ **Datos completos**: Nombre, apellido, domicilio son obligatorios

### 9.4. Validaciones de Peticiones
- ✅ **Paciente existente**: La petición debe asociarse a un paciente registrado
- ✅ **Sucursal existente**: La petición debe asociarse a una sucursal activa
- ✅ **Fechas válidas**: Fecha de entrega debe ser posterior a fecha de carga
- ✅ **Obra social**: Campo obligatorio
- ✅ **Al menos una práctica**: Una petición debe tener al menos una práctica asociada

### 9.5. Validaciones de Prácticas y Resultados
- ✅ **Código único**: No pueden existir dos prácticas con el mismo código
- ✅ **Tipos de resultado**: NORMAL, CRITICO, RESERVADO
- ✅ **Detección automática de tipo**:
  - **RESERVADO**: Valores > 150 (requieren retiro presencial en sucursal)
  - **CRÍTICO**: Valores entre 100-150 (requieren atención médica inmediata)
  - **NORMAL**: Valores < 100 (dentro de parámetros esperados)
- ✅ **Resultado obligatorio**: No puede cargarse una práctica sin resultado
- ✅ **Valores numéricos o texto**: Sistema acepta ambos formatos

### 9.6. Control de Permisos por Rol

**ADMINISTRADOR** (Control total):
- ✅ Todas las operaciones del sistema
- ✅ Único rol que puede ser responsable técnico de sucursales
- ✅ Gestión de usuarios y sucursales

**LABORATORISTA** (Operaciones técnicas):
- ✅ Ver pacientes, peticiones y prácticas
- ✅ Cargar resultados de prácticas
- ✅ Ver resultados críticos
- ❌ No puede gestionar pacientes, sucursales ni usuarios

**RECEPCIONISTA** (Operaciones administrativas):
- ✅ Ver y gestionar pacientes
- ✅ Crear y ver peticiones
- ✅ Agregar prácticas a peticiones
- ❌ No puede cargar resultados ni gestionar sucursales/usuarios

---

## 10. Conclusión

Este sistema proporciona una solución completa e integral para la gestión de laboratorios de análisis clínicos, automatizando procesos manuales, reduciendo errores y mejorando la eficiencia operativa. La implementación de este sistema permitirá a los laboratorios modernizar sus operaciones y prepararse para futuras expansiones.

---

**Proyecto desarrollado para:** UADE - Paradigma de Objetos  
**Tecnología:** Java + Swing + JSON  
**Arquitectura:** MVC con separación de capas

