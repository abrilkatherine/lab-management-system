# 🚀 Inicio Rápido - Sistema de Gestión de Laboratorio

## ⚡ Comandos Esenciales

### 🎯 Para empezar AHORA:

```bash
# 1. Ejecutar el proyecto (comando principal)
make run
```

### 📋 Comandos Básicos

| Comando | Descripción | Uso |
|---------|-------------|-----|
| `make run` | **Ejecutar proyecto** | `make run` |
| `make build` | Build completo | `make build` |
| `make run-tests` | Ejecutar pruebas | `make run-tests` |
| `make clean` | Limpiar todo | `make clean` |

### 🔧 Con Make (RECOMENDADO)

```bash
# Ejecutar el proyecto (comando principal)
make run

# Build completo y ejecutar
make build && make run

# Ejecutar pruebas
make run-tests

# Limpiar
make clean
```

## 🎮 Flujos de Trabajo

### Para Desarrollo:
```bash
make run  # Ejecuta la interfaz gráfica (RECOMENDADO)
# o
./run.sh  # Alternativa con script bash
```

### Para Pruebas:
```bash
make run-tests  # Ejecuta las pruebas del sistema
# o
./run-tests.sh  # Alternativa con script bash
```

### Para Limpiar:
```bash
make clean  # Limpia archivos compilados
# o
./clean.sh  # Alternativa con script bash
```

## ⚠️ Si algo falla:

1. **Verificar Java:**
   ```bash
   java -version
   ```

2. **Limpiar y recompilar:**
   ```bash
   ./clean.sh && ./build.sh
   ```

3. **Verificar dependencias:**
   ```bash
   ls lib/gson-2.10.1.jar
   ```

## 🎉 ¡Listo!

Una vez que ejecutes `./run.sh`, deberías ver la interfaz gráfica del sistema de gestión de laboratorio.

---

**💡 Tip:** Usa `./build.sh` antes de `./run.sh` para asegurar que todo esté compilado correctamente.
