#!/bin/bash

# Script de ejecución de pruebas para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Ejecuta las pruebas del Main.java

echo "🧪 Ejecutando pruebas del Sistema de Gestión de Laboratorio..."

# Verificar si el proyecto está compilado
if [ ! -d "out/classes" ] || [ ! -f "out/classes/main/uade/edu/ar/Main.class" ]; then
    echo "⚠️  El proyecto no está compilado. Compilando automáticamente..."
    ./compile.sh
    if [ $? -ne 0 ]; then
        echo "❌ Error al compilar el proyecto"
        exit 1
    fi
fi

# Ejecutar las pruebas
echo "🔬 Ejecutando pruebas de funcionalidad..."
java -cp "out/classes:lib/gson-2.10.1.jar" main.uade.edu.ar.Main

echo "✅ Pruebas completadas"
