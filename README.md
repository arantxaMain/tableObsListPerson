# Person TableView Application

Una aplicación JavaFX que permite gestionar una tabla de personas con funcionalidades para agregar, eliminar y restaurar registros.

## Descripción

Esta aplicación proporciona una interfaz gráfica simple para:
- Visualizar una tabla de personas con datos como ID, nombre, apellido y fecha de nacimiento
- Agregar nuevas personas a la tabla
- Eliminar personas seleccionadas
- Restaurar la tabla a su estado inicial
- Validación de datos de entrada

## Características

- **Interfaz intuitiva**: Formulario simple con campos de entrada y botones de acción
- **Validación de datos**: Verificación automática de nombres, apellidos y fechas de nacimiento
- **Gestión de tabla**: Agregar, eliminar y restaurar registros
- **Categorización por edad**: Clasificación automática en BABY, CHILD, TEEN, ADULT, SENIOR
- **Arquitectura modular**: Separación clara entre modelo, vista y controlador

## Tecnologías

- **Java 21+**
- **JavaFX 21+**
- **FXML** para el diseño de la interfaz
- **SLF4J** para logging

## Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   ├── es/arantxa/
│   │   │   ├── App.java                    # Clase principal
│   │   │   ├── controller/
│   │   │   │   └── TablaController.java    # Controlador de la interfaz
│   │   │   ├── model/
│   │   │   │   └── Person.java             # Modelo de datos
│   │   │   └── util/
│   │   │       └── PersonTableUtil.java    # Utilidades para la tabla
│   │   └── module-info.java                # Configuración del módulo
│   └── resources/
│       └── fxml/
│           └── tabla.fxml                  # Diseño de la interfaz
```

## Requisitos

- Java 21 o superior
- JavaFX 21 o superior
- Maven (si se usa como sistema de construcción)

## Instalación y ejecución

### Con Maven

1. Clona o descarga el proyecto
2. Navega al directorio del proyecto
3. Ejecuta:
   ```bash
   mvn clean javafx:run
   ```

### Con IDE

1. Importa el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, VS Code)
2. Asegúrate de que JavaFX esté configurado en el classpath
3. Ejecuta la clase `es.arantxa.App`

### Argumentos JVM necesarios

Si encuentras problemas de acceso de módulos, añade estos argumentos JVM:

```bash
--add-opens javafx.base/javafx.beans.property=ALL-UNNAMED
--add-opens javafx.controls/javafx.scene.control=ALL-UNNAMED
```

## Uso

### Agregar una persona
1. Completa los campos "First Name", "Last Name" y "Birth Date"
2. Haz clic en el botón "Add"
3. La persona se agregará a la tabla si los datos son válidos

### Eliminar personas
1. Selecciona una o más filas en la tabla
2. Haz clic en "Delete Selected Rows"
3. Las filas seleccionadas se eliminarán

### Restaurar datos
1. Haz clic en "Restore Rows"
2. La tabla volverá a mostrar los datos iniciales

## Validaciones

La aplicación valida:
- **Nombre**: Debe contener al menos un carácter no vacío
- **Apellido**: Debe contener al menos un carácter no vacío
- **Fecha de nacimiento**: No puede estar en el futuro

## Categorías de edad

El sistema clasifica automáticamente a las personas según su edad:
- **BABY**: 0-1 años
- **CHILD**: 2-12 años
- **TEEN**: 13-19 años
- **ADULT**: 20-50 años
- **SENIOR**: 51+ años
- **UNKNOWN**: Sin fecha de nacimiento

## Configuración de módulos

El proyecto incluye un archivo `module-info.java` que configura:
- Dependencias requeridas (JavaFX, SLF4J)
- Apertura de paquetes para JavaFX
- Exportación de paquetes públicos

## Problemas comunes

### Error de acceso a módulos
Si ves errores como "module javafx.base cannot access class", asegúrate de que:
1. El archivo `module-info.java` esté presente
2. Incluya la línea: `opens es.arantxa.model to javafx.base;`

### JavaFX no encontrado
Si JavaFX no se encuentra:
1. Verifica que JavaFX esté en el classpath
2. Usa los argumentos JVM mencionados anteriormente
3. Considera usar OpenJFX con Maven

## Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## Autor

Desarrollado como proyecto educativo para aprender JavaFX y patrones MVC.