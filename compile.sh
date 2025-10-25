#!/bin/bash

# Script de compilación para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Compila el proyecto Java con todas las dependencias

echo "🔨 Compilando Sistema de Gestión de Laboratorio..."

# Crear directorio de salida si no existe
mkdir -p out/classes

# Limpiar compilaciones anteriores
echo "🧹 Limpiando compilaciones anteriores..."
rm -rf out/classes/*

# Compilar el proyecto
echo "📦 Compilando clases Java..."
javac -cp "lib/gson-2.10.1.jar" \
      -d out/classes \
      src/main/uade/edu/ar/*.java \
      src/main/uade/edu/ar/controller/*.java \
      src/main/uade/edu/ar/dao/*.java \
      src/main/uade/edu/ar/dto/*.java \
      src/main/uade/edu/ar/enums/*.java \
      src/main/uade/edu/ar/mappers/*.java \
      src/main/uade/edu/ar/model/*.java \
      src/main/uade/edu/ar/util/*.java \
      src/main/uade/edu/ar/vista/*.java

# Verificar si la compilación fue exitosa
if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa!"
    echo "📁 Clases compiladas en: out/classes/"
else
    echo "❌ Error en la compilación"
    exit 1
fi
