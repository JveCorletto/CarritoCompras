# Carrito de Compras - Spring Boot Project

## Descripción del Proyecto

Este proyecto es un **carrito de compras** construido con **Spring Boot**. Proporciona una API que permite gestionar productos, compras, y detalles de las compras, con autenticación de usuarios. Utiliza una base de datos MySQL para almacenar los datos y está preparado para ser extendido con más funcionalidades.

### Características principales
- Gestión de productos (CRUD).
- Gestión de compras y detalles de las compras.
- Autenticación de usuarios.
- Integración con Thymeleaf para el frontend (en desarrollo).

## Requisitos previos

Antes de ejecutar este proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Java 17** o superior.
- **Maven 3.8.4** o superior.
- **MySQL** o cualquier base de datos compatible con JPA.
- **IntelliJ IDEA** (recomendado para desarrollo).

## Instalación y Configuración

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/tu_usuario/CarritoCompras-main.git
   cd CarritoCompras-main
   ```

2. **Configurar la base de datos**

   Crea una base de datos MySQL con el nombre `carrito_compras_db`. Luego, en el archivo `src/main/resources/application.properties`, configura las credenciales de tu base de datos:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/carrito_compras_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Compilar el proyecto**

   Desde la raíz del proyecto, ejecuta el siguiente comando para compilar el proyecto con Maven:

   ```bash
   ./mvnw clean install
   ```

## Ejecución de la Aplicación

1. **Ejecutar la aplicación**

   Una vez configurada la base de datos y compilado el proyecto, puedes ejecutar la aplicación usando:

   ```bash
   ./mvnw spring-boot:run
   ```

   La aplicación estará disponible en `http://localhost:8080`.

2. **Acceso a la API**

   La API principal estará disponible en la ruta `/api/v1/compras`, donde podrás gestionar las compras y sus detalles.

## Uso de la Aplicación

### Endpoints principales:

- **Productos**
  - `GET /api/v1/productos`: Listar todos los productos.
  - `POST /api/v1/productos`: Crear un nuevo producto.
  - `PUT /api/v1/productos/{id}`: Actualizar un producto.
  - `DELETE /api/v1/productos/{id}`: Eliminar un producto.

- **Compras**
  - `GET /api/v1/compras`: Listar todas las compras.
  - `POST /api/v1/compras`: Crear una nueva compra.
  - `PUT /api/v1/compras/{id}`: Actualizar una compra.
  - `DELETE /api/v1/compras/{id}`: Eliminar una compra.

- **Detalle de Compras**
  - `GET /api/v1/compras/{id}/detalle`: Listar el detalle de una compra.
  - `POST /api/v1/compras/{id}/detalle`: Agregar un detalle a una compra.

### Ejecución de pruebas

Para ejecutar las pruebas unitarias y de integración incluidas, puedes usar el siguiente comando de Maven:

```bash
./mvnw test
```

## Base de datos

Los scripts SQL necesarios para crear las tablas y poblar la base de datos inicial se encuentran en la carpeta `sql`.

## Estructura del Proyecto

- `src/main/java`: Código fuente principal de la aplicación.
- `src/main/resources`: Archivos de configuración, incluyendo `application.properties`.
- `sql`: Scripts SQL para la base de datos.
- `target`: Archivos generados después de compilar.

## Contribuciones

Las contribuciones a este proyecto son bienvenidas. Por favor, realiza un fork del repositorio y envía tus pull requests para revisión.

## Licencia

Este proyecto está bajo la Licencia MIT.
"""

# Write the content to a README.md file in the project folder
readme_file_path = os.path.join(project_folder_path, 'README.md')
with open(readme_file_path, 'w') as readme_file:
    readme_file.write(readme_content)

# Check that the file was created
os.listdir(project_folder_path)
