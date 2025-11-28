# 🔧 Guía de Configuración Rápida

## ✅ Requisitos

- **Java:** JDK 21 o superior
- **Make:** Para compilar (macOS/Linux incluido, Windows ver abajo)
- **Git:** Para clonar el proyecto

## 📥 Instalación

### 1. Instalar Java

**macOS:**
```bash
brew install openjdk@21
```

**Windows/Linux:** Descargar desde https://adoptium.net/ → JDK 21 LTS

**Verificar:**
```bash
java -version  # Debe mostrar versión 21+
```

### 2. Clonar y Compilar

```bash
git clone <URL_DEL_REPO>
cd lab-management-system
make build
```

### 3. Ejecutar

```bash
make run
```

---

## 🎨 Configurar IDE

### IntelliJ IDEA
1. File → Open → Seleccionar carpeta del proyecto
2. File → Project Structure → SDK: JDK 21+
3. Marcar `src/main` como Sources Root
4. Agregar `lib/gson-2.11.0.jar` como Library

### VS Code
1. Instalar extensión: "Extension Pack for Java"
2. Abrir carpeta del proyecto
3. Cmd/Ctrl+Shift+P → "Java: Configure Runtime" → JDK 21+

---

## 📦 Dependencias

✅ **Todas las librerías están incluidas** en `lib/`
- Gson 2.11.0 (ya incluido, no descargar nada)

---

## 🚀 Comandos

```bash
make build      # Compilar
make run        # Ejecutar
make run-tests  # Tests
make clean      # Limpiar
make help       # Ver más comandos
```

