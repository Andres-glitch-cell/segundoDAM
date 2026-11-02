#!/bin/bash

echo "🚀 Iniciando reparación y verificación de proyectos Gradle en BaseDatos..."
echo "------------------------------------------------------------------------"

# Buscar todas las carpetas que contengan un archivo gradlew
find . -maxdepth 3 -name "gradlew" | while read -r gradlew_path; do
    # Obtener la carpeta contenedora del proyecto
    project_dir=$(dirname "$gradlew_path")
    
    echo "📁 Procesando proyecto en: $project_dir"
    
    # Entrar a la carpeta del proyecto
    cd "$project_dir" || continue
    
    # 1. Corregir formato de saltos de línea Windows (CRLF) a Linux (LF)
    if [ -f "gradlew" ]; then
        sed -i 's/\r$//' gradlew
        chmod +x gradlew
    fi
    
    # 2. Ejecutar la compilación de prueba para verificar que todo está correcto
    echo "⏳ Compilando con ./gradlew clean build..."
    ./gradlew clean build -q
    
    if [ $? -eq 0 ]; then
        echo "✅ ¡Proyecto correcto y verificado!"
    else
        echo "❌ Error al compilar este proyecto. Revisa los logs de arriba."
    fi
    
    echo "------------------------------------------------------------------------"
    
    # Volver a la carpeta raíz de BaseDatos para continuar el bucle
    cd - > /dev/null || exit
done

echo "🎉 ¡Proceso finalizado con éxito!"
