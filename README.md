# Sistema de Evaluación con Taxonomía de Bloom

## Descripción

Sistema desarrollado en Java Swing que permite administrar la aplicación de pruebas cuyos ítemes están especificados según la taxonomía de Bloom. El sistema soporta preguntas de selección múltiple y verdadero/falso con diferentes niveles taxonómicos.

## Características Principales

- 🎯 Soporte para preguntas de selección múltiple y verdadero/falso
- 📊 Evaluación basada en la Taxonomía de Bloom
- 📝 Carga de preguntas desde archivo JSON
- 📈 Análisis detallado de resultados
- 🔄 Navegación intuitiva entre preguntas
- 📱 Interfaz gráfica moderna con Java Swing

## Funcionalidades Implementadas

### 1. Carga de ítemes desde archivo
- Carga automática de preguntas desde archivo JSON al presionar el botón "Cargar Preguntas"
- Muestra información de la prueba: cantidad de ítemes y tiempo total estimado
- Validación y manejo de errores en la carga de archivos

### 2. Aplicación de la prueba
- Navegación entre preguntas con botones "Anterior" y "Siguiente"
- Indicador de progreso que muestra preguntas respondidas
- Mantiene las respuestas del usuario al navegar
- Validación antes de finalizar el quiz
- Botón "Anterior" deshabilitado en primera pregunta
- Botón cambia a "Enviar respuestas" en última pregunta

### 3. Revisión de respuestas
- **Resumen estadístico** con:
  - Porcentaje general de respuestas correctas
  - Porcentaje por tipo de pregunta (Selección Múltiple y Verdadero/Falso)
  - Porcentaje por nivel de taxonomía de Bloom
- **Revisión individual** que permite:
  - Navegar pregunta por pregunta mostrando respuestas correctas e incorrectas
  - Visualización clara de la respuesta del usuario vs respuesta correcta
  - Información adicional como nivel de Bloom y tiempo estimado
  - Navegación con botones "Anterior" y "Siguiente"
  - Botón "Volver al resumen" para regresar a las estadísticas

## Instrucciones de Ejecución

### Requisitos Previos
- IntelliJ IDEA
- JDK 23.0.2

### Ejecutar el Proyecto

1. **Abrir el proyecto:**
   - Abrir IntelliJ IDEA
   - Seleccionar "Open" y navegar hasta la carpeta del proyecto
   - Seleccionar la carpeta `tarea2` y hacer clic en "OK"

2. **Ejecutar la aplicación:**
   - Localizar `src/Main.java` en el panel de Project
   - Hacer clic derecho en `Main.java`
   - Seleccionar "Run 'Main.main()'"
   - O usar el botón verde de "Run" en la barra de herramientas

### Uso de la Aplicación

1. Iniciar la aplicación
2. Presionar "Cargar Preguntas" para cargar el archivo de preguntas
3. Seleccionar "Iniciar Quiz" para comenzar la evaluación
4. Navegar entre preguntas usando los botones de navegación
5. Revisar los resultados al finalizar

## Formato del Archivo de Preguntas

### Especificación del archivo `preguntas.json`

El archivo debe estar ubicado en `src/data/preguntas.json` y seguir el siguiente formato JSON:

```json
[
  {
    "type": "SeleccionMultiple",
    "enunciado": "¿Cuál es la capital de Francia?",
    "bloomLevel": "RECORDAR",
    "tiempoEstimado": 30,
    "opciones": ["Madrid", "París", "Berlín", "Lisboa"],
    "indiceRespuestaCorrecta": 1
  },
  {
    "type": "VerdaderoFalso",
    "enunciado": "El agua hierve a 100°C a nivel del mar.",
    "bloomLevel": "RECORDAR",
    "tiempoEstimado": 15,
    "respuestaCorrecta": true,
    "justificacion": "A nivel del mar, el agua hierve a 100 grados Celsius."
  }
]
```

### Campos obligatorios:

#### Para ambos tipos de preguntas:
- `type`: "SeleccionMultiple" o "VerdaderoFalso"
- `enunciado`: Texto de la pregunta
- `bloomLevel`: Nivel de taxonomía ("RECORDAR", "ENTENDER", "APLICAR", "ANALIZAR", "EVALUAR", "CREAR")
- `tiempoEstimado`: Tiempo en segundos (número entero o decimal)

#### Para Selección Múltiple:
- `opciones`: Array de strings con las opciones
- `indiceRespuestaCorrecta`: Índice de la respuesta correcta (0-based)

#### Para Verdadero/Falso:
- `respuestaCorrecta`: true o false
- `justificacion`: Texto explicativo (especialmente importante cuando la respuesta es false)

## Arquitectura del Sistema

### Paquetes
- **`tarea2.backend`**: Lógica de negocio y manejo de datos
  - `QuizManager`: Controlador principal del quiz
  - `Pregunta`: Clase abstracta base para preguntas
  - `SeleccionMultiple`: Implementación para preguntas de selección múltiple
  - `VerdaderoFalso`: Implementación para preguntas verdadero/falso
  - `BloomLevel`: Enumeración de niveles de taxonomía
  - `ResultadosRevision`: Clase para almacenar estadísticas de resultados

- **`tarea2.frontend`**: Interfaz gráfica de usuario
  - `DisplayMainMenu`: Menú principal de la aplicación
  - `PruebaPreguntas`: Pantalla principal del quiz
  - `PreguntasDisplay`: Manejo de visualización de preguntas
  - `Resultados`: Pantalla de resultados y estadísticas
  - `RevisionIndividual`: Navegación individual de respuestas
  - `Alternativa`: Componente para opciones de respuesta

### Validaciones Implementadas

1. **Archivo de preguntas:**
   - Verifica existencia del archivo en múltiples rutas
   - Valida formato JSON correcto
   - Verifica campos obligatorios
   - Maneja preguntas con formato incorrecto (las omite y continúa)

2. **Durante el quiz:**
   - Validación de respuestas completas antes de finalizar
   - Confirmación cuando hay preguntas sin responder
   - Navegación controlada (botones habilitados/deshabilitados según contexto)

3. **Manejo de errores:**
   - Mensajes informativos para el usuario
   - Logs en consola para debugging
   - Recuperación graceful de errores

## Supuestos y Alcances

### Supuestos:
- El archivo de preguntas está en formato JSON válido
- Los índices de respuestas correctas están dentro del rango válido
- Los niveles de Bloom están escritos correctamente
- El usuario tiene permisos de lectura en el directorio de datos

### Limitaciones:
- Solo soporta tipos de pregunta: Selección Múltiple y Verdadero/Falso
- Archivo de preguntas en formato JSON únicamente
- No hay persistencia de resultados entre sesiones
- No hay funcionalidad de edición de preguntas desde la interfaz

### Extensiones posibles:
- Soporte para más tipos de preguntas
- Guardado/carga de resultados
- Editor visual de preguntas
- Temporizador durante el quiz
- Exportación de resultados a diferentes formatos

## Niveles de Taxonomía de Bloom Soportados

1. **RECORDAR**: Memorización y almacenamiento de datos básicos
2. **ENTENDER**: Explicar, describir y discutir el conocimiento
3. **APLICAR**: Llevar a la práctica lo aprendido
4. **ANALIZAR**: Diferenciar, organizar y contrastar información
5. **EVALUAR**: Evaluar, valorar y criticar lo aprendido
6. **CREAR**: Producir y desarrollar nuevo conocimiento original 

## Estructura del Proyecto

```
tarea2/
├── src/
│   ├── data/
│   │   └── preguntas.json    # Archivo de preguntas
│   ├── tarea2/
│   │   ├── backend/         # Lógica de negocio
│   │   │   ├── QuizManager.java
│   │   │   ├── Pregunta.java
│   │   │   └── ...
│   │   └── frontend/        # Interfaz gráfica
│   │       ├── DisplayMainMenu.java
│   │       ├── PruebaPreguntas.java
│   │       └── ...
│   └── Main.java           # Punto de entrada
└── lib/
    └── gson-2.8.9.jar      # Dependencias
```


## Autores

José Salazar - Victor Nuñez - Raphael Maufroid

Link del Proyecto: [https://github.com/JoseISC/tarea2](https://github.com/JoseISC/tarea2) 