#!/bin/bash

# Script de limpieza para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Limpia archivos compilados y temporales

echo "🧹 Limpiando archivos del proyecto..."

# Limpiar clases compiladas
if [ -d "out/classes" ]; then
    echo "🗑️  Eliminando clases compiladas..."
    rm -rf out/classes/*
    echo "✅ Clases compiladas eliminadas"
else
    echo "ℹ️  No hay clases compiladas para eliminar"
fi

# Limpiar archivos temporales del sistema
echo "🧽 Limpiando archivos temporales..."
find . -name "*.class" -type f -delete 2>/dev/null
find . -name ".DS_Store" -type f -delete 2>/dev/null
find . -name "Thumbs.db" -type f -delete 2>/dev/null

echo "✅ Limpieza completada"
