#!/bin/bash

# Script de ejecución para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Ejecuta el proyecto con la interfaz gráfica

echo "🚀 Iniciando Sistema de Gestión de Laboratorio..."

# Verificar si el proyecto está compilado
if [ ! -d "out/classes" ] || [ ! -f "out/classes/main/uade/edu/ar/Menu.class" ]; then
    echo "⚠️  El proyecto no está compilado. Compilando automáticamente..."
    ./compile.sh
    if [ $? -ne 0 ]; then
        echo "❌ Error al compilar el proyecto"
        exit 1
    fi
fi

# Ejecutar la aplicación con interfaz gráfica
echo "🖥️  Ejecutando interfaz gráfica..."
java -cp "out/classes:lib/gson-2.10.1.jar" main.uade.edu.ar.vista.Menu

echo "👋 Aplicación finalizada"
