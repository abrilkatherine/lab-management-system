#!/bin/bash

# Script de build completo para el Sistema de Gestión de Laboratorio
# Autor: Sistema de Gestión de Laboratorio
# Descripción: Build completo del proyecto (limpiar, compilar, verificar)

echo "🏗️  Iniciando build completo del Sistema de Gestión de Laboratorio..."

# Paso 1: Limpiar
echo "📋 Paso 1/3: Limpiando proyecto..."
./clean.sh

# Paso 2: Compilar
echo "📋 Paso 2/3: Compilando proyecto..."
./compile.sh

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación. Build fallido."
    exit 1
fi

# Paso 3: Verificar
echo "📋 Paso 3/3: Verificando build..."

# Verificar que las clases principales existen
required_classes=(
    "out/classes/main/uade/edu/ar/Menu.class"
    "out/classes/main/uade/edu/ar/Main.class"
    "out/classes/main/uade/edu/ar/controller/PeticionController.class"
    "out/classes/main/uade/edu/ar/controller/PacienteController.class"
    "out/classes/main/uade/edu/ar/controller/SucursalYUsuarioController.class"
)

echo "🔍 Verificando clases principales..."
for class in "${required_classes[@]}"; do
    if [ -f "$class" ]; then
        echo "✅ $class"
    else
        echo "❌ $class - FALTANTE"
        echo "❌ Build fallido - clases principales no encontradas"
        exit 1
    fi
done

echo "🎉 Build completado exitosamente!"
echo "📁 Clases compiladas en: out/classes/"
echo "🚀 Para ejecutar: ./run.sh"
echo "🧪 Para pruebas: ./run-tests.sh"
