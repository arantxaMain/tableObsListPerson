# Person TableView Application

Una aplicación JavaFX que permite gestionar una tabla de personas con funcionalidades para agregar, eliminar y restaurar registros.

## 📝 Descripción

Esta aplicación proporciona una interfaz gráfica simple para:
- Visualizar una tabla de personas con datos como ID, nombre, apellido y fecha de nacimiento
- Agregar nuevas personas a la tabla
- Eliminar personas seleccionadas
- Restaurar la tabla a su estado inicial
- Validación de datos de entrada

## ✨ Características

- **Interfaz intuitiva**: Formulario simple con campos de entrada y botones de acción
- **Validación de datos**: Verificación automática de nombres, apellidos y fechas de nacimiento
- **Gestión de tabla**: Agregar, eliminar y restaurar registros
- **Categorización por edad**: Clasificación automática en BABY, CHILD, TEEN, ADULT, SENIOR
- **Arquitectura modular**: Separación clara entre modelo, vista y controlador

## 🛠 Tecnologías

- **Java 21+**
- **JavaFX 21+**
- **FXML** para el diseño de la interfaz
- **SLF4J** para logging

## 📁 Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   ├── es/arantxa/
│   │   │   ├── App.java                    # Clase principal
│   │   │   ├── Lanzador.java               # Clase de lanzamiento
│   │   │   ├── connection/
│   │   │   │   └── DBConnection.java       # Conexión a la base de datos
│   │   │   ├── controller/
│   │   │   │   └── TablaController.java    # Controlador de la interfaz
│   │   │   ├── dao/
│   │   │   │   └── PersonDAO.java          # Acceso a datos de personas
│   │   │   ├── model/
│   │   │   │   └── Person.java             # Modelo de datos
│   │   │   └── util/
│   │   │       ├── AlertUtil.java          # Utilidades para alertas
│   │   │       └── PersonTableUtil.java    # Utilidades para la tabla
│   │   └── module-info.java                # Configuración del módulo
│   └── resources/
│       ├── db.properties                   # Configuración de la base de datos
│       ├── logback.xml                     # Configuración de logging
│       ├── META-INF/
│       │   └── MANIFEST.MF                 # Manifiesto
│       └── fxml/
│           └── tabla.fxml                  # Diseño de la interfaz
```

## 🔗 Conexión a la base de datos

La aplicación utiliza una base de datos para persistir la información de las personas. La configuración se realiza mediante el archivo `db.properties` en el directorio `resources`.

```properties
db.url=url de la base de datos
db.user=usuario
db.password=contraseña
```
> ⚠️ Este archivo se incluirá en el repositorio con valores vacíos para que cada desarrollador configure sus propias credenciales localmente.


## 💻 Requisitos

- Java 21 o superior
- JavaFX 21 o superior
- Maven (si se usa como sistema de construcción)

## 🚀 Instalación y ejecución

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

## 🧩 Uso

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

## ✅ Validaciones

La aplicación valida:
- **Nombre**: Debe contener al menos un carácter no vacío
- **Apellido**: Debe contener al menos un carácter no vacío
- **Fecha de nacimiento**: No puede estar en el futuro



## 📦 Configuración de módulos

El proyecto incluye un archivo `module-info.java` que configura:
- Dependencias requeridas (JavaFX, SLF4J)
- Apertura de paquetes para JavaFX
- Exportación de paquetes públicos

## ⚠️ Problemas comunes

### Error de acceso a módulos
Si ves errores como "module javafx.base cannot access class", asegúrate de que:
1. El archivo `module-info.java` esté presente
2. Incluya la línea: `opens es.arantxa.model to javafx.base;`

### JavaFX no encontrado
Si JavaFX no se encuentra:
1. Verifica que JavaFX esté en el classpath
2. Usa los argumentos JVM mencionados anteriormente
3. Considera usar OpenJFX con Maven



## 👨‍💻 Autor

Arantxa Maín