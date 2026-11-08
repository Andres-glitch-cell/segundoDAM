# Walkthrough - UD02. Actividad 07 – Diálogos

Implementación completa de la Actividad 07 de UD02 (Diálogos) en Android con Java y XML.

## Cambios Realizados

### Configuración del Proyecto y Estructura
- [NEW] [build.gradle.kts](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/build.gradle.kts): Configuración del módulo de Android con dependencias de AppCompat, Material Design y ConstraintLayout.
- [NEW] [AndroidManifest.xml](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/AndroidManifest.xml): Declaración de las tres Activities (`MainActivity`, `Ejercicio1Activity`, `Ejercicio2Activity`).

### Recursos XML (`res/`)
- [NEW] [strings.xml](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/res/values/strings.xml): Textos y títulos de la aplicación en español.
- [NEW] [activity_main.xml](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/res/layout/activity_main.xml): Menú principal con dos botones para acceder a los ejercicios.
- [NEW] [activity_ejercicio1.xml](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/res/layout/activity_ejercicio1.xml): Layout para el Ejercicio 1 (dos botones para diálogos de botones y listado).
- [NEW] [activity_ejercicio2.xml](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/res/layout/activity_ejercicio2.xml): Layout para el Ejercicio 2 (dos botones para selector de fecha y hora).

### Código Java (`src/main/java/com/example/ud02_dialogs/`)
- [NEW] [MainActivity.java](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/java/com/example/ud02_dialogs/MainActivity.java): Activity principal que enlaza con los dos ejercicios.
- [NEW] [Ejercicio1Activity.java](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/java/com/example/ud02_dialogs/Ejercicio1Activity.java): Implementa:
  1. `AlertDialog` con botones (Aceptar, Cancelar, Neutral) y Toast de respuesta.
  2. `AlertDialog` con listado de los días de la semana y Toast con el día seleccionado.
- [NEW] [Ejercicio2Activity.java](file:///E:/backUpInsti-EXTERNALDISK/00_DAM/androidStudioCarlos/UD02_Dialogs/src/main/java/com/example/ud02_dialogs/Ejercicio2Activity.java): Implementa:
  1. `DatePickerDialog` para seleccionar fecha con Toast al pulsar Aceptar.
  2. `TimePickerDialog` para seleccionar hora con Toast al pulsar Aceptar.

## Verificación
- El proyecto compila correctamente (`BUILD SUCCESSFUL`) con Gradle.
- Todos los requisitos solicitados en la actividad de diálogos están cubiertos al 100%.
