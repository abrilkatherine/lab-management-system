# Makefile para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Comandos make para facilitar el desarrollo

# Variables
JAVA = java
JAVAC = javac
JAR = jar
CP = lib/gson-2.10.1.jar
SRC_DIR = src/main/uade/edu/ar
OUT_DIR = out/classes
LIB_DIR = lib

# Clases principales
MAIN_CLASS = main.uade.edu.ar.Main
GUI_CLASS = main.uade.edu.ar.vista.Menu

# Colores para output
GREEN = \033[0;32m
YELLOW = \033[1;33m
RED = \033[0;31m
NC = \033[0m # No Color

.PHONY: all compile run run-gui run-tests clean build help

# Comando por defecto
all: build

# Compilar el proyecto
compile:
	@echo "$(GREEN)🔨 Compilando Sistema de Gestión de Laboratorio...$(NC)"
	@mkdir -p $(OUT_DIR)
	@$(JAVAC) -cp "$(CP)" -d $(OUT_DIR) \
		$(SRC_DIR)/*.java \
		$(SRC_DIR)/controller/*.java \
		$(SRC_DIR)/dao/*.java \
		$(SRC_DIR)/dto/*.java \
		$(SRC_DIR)/enums/*.java \
		$(SRC_DIR)/exceptions/*.java \
		$(SRC_DIR)/mappers/*.java \
		$(SRC_DIR)/model/*.java \
		$(SRC_DIR)/util/*.java \
		$(SRC_DIR)/vista/*.java
	@echo "$(GREEN)✅ Compilación exitosa!$(NC)"

# Ejecutar interfaz gráfica (alias para run)
run-gui: run

# Ejecutar pruebas
run-tests: compile
	@echo "$(GREEN)🧪 Ejecutando pruebas...$(NC)"
	@$(JAVA) -cp "$(OUT_DIR):$(CP)" $(MAIN_CLASS)

# Ejecutar el proyecto (comando principal)
run: compile
	@echo "$(GREEN)🚀 Ejecutando Sistema de Gestión de Laboratorio...$(NC)"
	@$(JAVA) -cp "$(OUT_DIR):$(CP)" $(GUI_CLASS)

# Limpiar archivos compilados
clean:
	@echo "$(YELLOW)🧹 Limpiando archivos...$(NC)"
	@rm -rf $(OUT_DIR)/*
	@find . -name "*.class" -type f -delete 2>/dev/null || true
	@find . -name ".DS_Store" -type f -delete 2>/dev/null || true
	@echo "$(GREEN)✅ Limpieza completada$(NC)"

# Build completo
build: clean compile
	@echo "$(GREEN)🏗️  Build completado exitosamente!$(NC)"

# Crear JAR ejecutable
jar: compile
	@echo "$(GREEN)📦 Creando JAR ejecutable...$(NC)"
	@mkdir -p out/jar
	@$(JAR) cfm out/jar/lab-management-system.jar META-INF/MANIFEST.MF -C $(OUT_DIR) .
	@echo "$(GREEN)✅ JAR creado en out/jar/lab-management-system.jar$(NC)"

# Ejecutar desde JAR
run-jar: jar
	@echo "$(GREEN)🚀 Ejecutando desde JAR...$(NC)"
	@$(JAVA) -jar out/jar/lab-management-system.jar

# Verificar dependencias
check-deps:
	@echo "$(YELLOW)🔍 Verificando dependencias...$(NC)"
	@if [ ! -f "$(CP)" ]; then \
		echo "$(RED)❌ Error: $(CP) no encontrado$(NC)"; \
		exit 1; \
	fi
	@echo "$(GREEN)✅ Dependencias OK$(NC)"

# Mostrar ayuda
help:
	@echo "$(GREEN)Sistema de Gestión de Laboratorio - Comandos disponibles:$(NC)"
	@echo ""
	@echo "$(YELLOW)🎯 Comando Principal:$(NC)"
	@echo "  make run         - $(GREEN)Ejecutar el proyecto (GUI)$(NC)"
	@echo ""
	@echo "$(YELLOW)Compilación:$(NC)"
	@echo "  make compile     - Compilar el proyecto"
	@echo "  make build       - Build completo (limpiar + compilar)"
	@echo ""
	@echo "$(YELLOW)Ejecución:$(NC)"
	@echo "  make run         - Ejecutar interfaz gráfica (PRINCIPAL)"
	@echo "  make run-gui     - Ejecutar interfaz gráfica (alias)"
	@echo "  make run-tests   - Ejecutar pruebas"
	@echo ""
	@echo "$(YELLOW)Limpieza:$(NC)"
	@echo "  make clean       - Limpiar archivos compilados"
	@echo ""
	@echo "$(YELLOW)JAR:$(NC)"
	@echo "  make jar         - Crear JAR ejecutable"
	@echo "  make run-jar     - Ejecutar desde JAR"
	@echo ""
	@echo "$(YELLOW)Utilidades:$(NC)"
	@echo "  make check-deps  - Verificar dependencias"
	@echo "  make help        - Mostrar esta ayuda"
